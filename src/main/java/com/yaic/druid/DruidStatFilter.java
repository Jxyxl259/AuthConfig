package com.yaic.druid;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter; 


/** 
* @ClassName: DruidStatFilter 
* @Description: 配置监控拦截器
* @author zhaoZd
* @date 2018年6月19日 下午4:34:57 
*  
*/
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",initParams={    
    @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),// 忽略资源    
})   
public class DruidStatFilter extends WebStatFilter {  
  
}   