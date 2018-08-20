package com.yaic.auth;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.yaic.auth.common.PublicParamters;

/**
 * 权限配置类
 * 
 * @ClassName: ShiroConfig
 * @Description: TODO
 * @author zhaoZD
 * @date 2018年6月17日 下午9:27:03
 *
 */
@Configuration
public class ShiroConfig {

	
	/** 
	* @Title: shirFilter 
	* @Description: 权限拦截器
	* @param securityManager
	* @return    
	* @return ShiroFilterFactoryBean  
	* @throws 
	*/
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		//System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//登出拦截器.
		Map<String, Filter> filters = new HashMap<>();
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl("/login");
		filters.put("logout", logoutFilter);
		shiroFilterFactoryBean.setFilters(filters);
		 
		//如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/home");
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		
		//配置规则
		loadShiroFilterChain(shiroFilterFactoryBean);
		
		return shiroFilterFactoryBean;
	}
	
	/** 
	* @Title: loadShiroFilterChain 
	* @Description: 配置拦截规则
	* @param shiroFilterFactoryBean    
	* @return void  
	* @throws 
	*/
	private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		
		//配置不会被拦截的链接 顺序判断
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/swagger/**", "anon");
		filterChainDefinitionMap.put("/v2/api-docs", "anon");	 
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/lib/**", "anon");
		filterChainDefinitionMap.put("/checkBean/**", "anon");
		filterChainDefinitionMap.put("/authconfig/**", "anon");
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		 
		//登出
		filterChainDefinitionMap.put("/logout", "logout");
		 
		//authc：该过滤器下的页面必须登录后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		filterChainDefinitionMap.put("/user/**", "authc");
		filterChainDefinitionMap.put("/account/**", "authc");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		filterChainDefinitionMap.put("/**", "authc");
		 
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	}

	/** 
	* @Title: hashedCredentialsMatcher 
	* @Description: 凭证匹配器
	* @return    
	* @return HashedCredentialsMatcher  
	* @throws 
	*/
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName(PublicParamters.algorithmName);// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(PublicParamters.hashIterations);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	@Bean
	public ShiroRealm myShiroRealm() {
		ShiroRealm myShiroRealm = new ShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		securityManager.setSessionManager(defaultWebSessionManager());
		return securityManager;
	}

	/**
	 * @Title: authorizationAttributeSourceAdvisor @Description: 开启shiro aop注解支持.
	 *         使用代理方式;所以需要开启代码支持; @param securityManager @return @return
	 *         AuthorizationAttributeSourceAdvisor @throws
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean(name = "simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");// 数据库异常处理
		mappings.setProperty("UnauthorizedException", "403");
		r.setExceptionMappings(mappings); // None by default
		r.setDefaultErrorView("error"); // No default
		r.setExceptionAttribute("ex"); // Default is "exception"
		// r.setWarnLogCategory("example.MvcLogger"); // No default
		return r;
	}


	@Bean(name="defaultWebSessionManager")
	public DefaultWebSessionManager defaultWebSessionManager(){
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// session三个小时过期
		sessionManager.setGlobalSessionTimeout(3*60*60*1000L);
		return sessionManager;
	}
}