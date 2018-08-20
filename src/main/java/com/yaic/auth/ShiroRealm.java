package com.yaic.auth;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yaic.auth.common.Constants;
import com.yaic.auth.common.PublicParamters;
import com.yaic.auth.interior.common.TreeNode;
import com.yaic.auth.interior.model.AppUserModel;
import com.yaic.auth.interior.service.AppUserRoleService;
import com.yaic.auth.interior.service.AppUserService;
import com.yaic.auth.thirdparty.controller.AccountController;

/**
 * 
* @ClassName: MyShiroRealm 
* @Description: TODO
* @author zhaoZD
* @date 2018年6月17日 下午10:05:40 
*
 */
public class ShiroRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private AppUserService appUserService;
    
    @Resource
    AppUserRoleService appUserRoleService;
    
    
    
    /** 
    * Title: doGetAuthorizationInfo
    * Description: 获取权限配置信息(角色和资源) 
    * @param principals
    * @return 
    */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	String userCode = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<TreeNode> roles;
		try {
			roles = appUserRoleService.findMyRolesByUserCode(userCode);
			String[] roleArr = new String[roles.size()];
	        
	        TreeNode node = null;
	        for(int i=0;i< roles.size() ;i++){
	        	
	            node = roles.get(i) ;
	            roleArr[i] = node.getId();
	        }
			Set<String> roleSet = new HashSet<String>(Arrays.asList(roleArr));
	        if(roleSet.contains(null)){
	            roleSet.remove(null);
	        }
	        authorizationInfo.setRoles(roleSet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
//        List<AppRoleResourceDto> roleResources = userService.findUserResources(userCode) ;
//        String[] roleResourcesArr = new String[roleResources.size()];
//        for(int i=0;i< roleResources.size() ;i++){
//            if(!StringUtils.isEmpty(roleResources.get(i)) && !StringUtils.isEmpty(roleResources.get(i).getResourceId())){
//                roleResourcesArr[i] = roleResources.get(i).getResourceId();
//            }
//        }
//        Set<String> permissionSet = new HashSet(Arrays.asList(roleResourcesArr));
//        if(permissionSet.contains(null)){
//            //非空校验
//            permissionSet.remove(null);
//        }
//        authorizationInfo.setStringPermissions(permissionSet);

        return authorizationInfo;
    }


    /**
    * Title: doGetAuthenticationInfo
    * Description: 用户账号密码验证
    * @param token
    * @return
    * @throws AuthenticationException 
    * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken) 
    */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
    	
//      System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
    	
        //获取用户的输入的账号.
        String userCode = (String)token.getPrincipal();
        logger.info("登录验证,获取输入的账号:{}",userCode);
        
        /*
         *此处可添加shiro时间间隔机制 
         */
        AppUserModel userInfo;
        SimpleAuthenticationInfo authenticationInfo = null;
		try {
			userInfo = appUserService.getInfoByUserCode(userCode);
			  if(userInfo == null){
		            return null;
		        }
		        authenticationInfo = new SimpleAuthenticationInfo(
		        		userInfo.getUserCode(), //用户名
		        		userInfo.getPassword(), //密码
		                ByteSource.Util.bytes(userInfo.getUserCode() + PublicParamters.SALT),//salt=usercode + cms
		                getName()  //realm name
		        );
		        
		        Session session = SecurityUtils.getSubject().getSession();
		        session.setTimeout(600000);//十分钟,毫秒单位
		        
		        session.setAttribute(Constants.LOGIN_USER_CODE_KEY,userInfo.getUserCode());
		        session.setAttribute(Constants.LOGIN_USER_ID_KEY,userInfo.getUserId());
		        session.setAttribute(Constants.CURRENT_USER_KEY,userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
      
        
        return authenticationInfo;
    }

}