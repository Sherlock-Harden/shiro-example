package org.example.shirp.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.example.shirp.constant.ShiroConstants;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yuansj[yuansj@neusoft.com]
 * @date 2021/01/04
 **/
@Component
@Configuration
public class ShiroConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
    defaultAAP.setProxyTargetClass(true);
    return defaultAAP;
  }

  @Bean
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    // 散列算法:这里使用MD5算法;
    hashedCredentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
    // 散列的次数，比如散列两次，相当于md5(md5(""));
    hashedCredentialsMatcher.setHashIterations(1);
    return hashedCredentialsMatcher;
  }

  @Bean
  public CustomRealm customRealm() {
    CustomRealm customRealm = new CustomRealm();
    customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    //开启缓存管理
    customRealm.setCacheManager(new EhCacheManager());
    //开启全局缓存设置为true
    customRealm.setCachingEnabled(true);
    //开启认证的缓存设置为true
    customRealm.setAuthenticationCachingEnabled(true);
    //给认证数据在缓存中设置一个名字
    customRealm.setAuthenticationCacheName("authenticationCache");
    //开启授权的缓存设置为true
    customRealm.setAuthorizationCachingEnabled(true);
    //给授权数据在缓存中设置一个名字
    customRealm.setAuthorizationCacheName("authorizationCache");
    return customRealm;
  }

  @Bean
  public DefaultWebSecurityManager securityManager(CustomRealm customRealm) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(customRealm);
    securityManager.setSessionManager(sessionManager());
    return securityManager;
  }

  @Bean
  public SimpleCookie simpleCookie() {
    SimpleCookie simpleCookie = new SimpleCookie(ShiroConstants.SHARD_SESSION_ID);
    simpleCookie.setHttpOnly(true);
    simpleCookie.setMaxAge(180);
    simpleCookie.setPath("/");
    return simpleCookie;
  }

  @Bean
  public SessionIdGenerator sessionIdGenerator() {
    return new JavaUuidSessionIdGenerator();
  }

  @Bean
  public RedisSessionDAO redisSessionDAO() {
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
    return redisSessionDAO;
  }

  @Bean
  public SessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(redisSessionDAO());
    sessionManager.setSessionIdCookie(simpleCookie());
    return sessionManager;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    //登录
    shiroFilterFactoryBean.setLoginUrl("/user/login");
    //首页
    shiroFilterFactoryBean.setSuccessUrl("/");
    //错误页面，认证不通过跳转
    shiroFilterFactoryBean.setUnauthorizedUrl("/error");
    // Map<String, Filter> filterMap = new LinkedHashMap<>();
    // LogoutFilter logoutFilter = new LogoutFilter();
    // logoutFilter.setRedirectUrl("/xxx/yyy");
    // filterMap.put("/logout", logoutFilter);
    // shiroFilterFactoryBean.setFilters(filterMap);
    Map<String, String> map = new LinkedHashMap<>();
    //对所有用户认证
    map.put("/user/login", "anon");
    map.put("/user/logout", "logout");
    map.put("/**", "anon");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
    return shiroFilterFactoryBean;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }
}
