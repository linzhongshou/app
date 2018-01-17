package cn.linzs.app.common.config;

import cn.linzs.app.common.filter.JwtFilter;
import cn.linzs.app.common.shiro.UserRealm;
import cn.linzs.app.common.utils.token.RedisTokenManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author linzs
 * @Date 2017-12-18 17:26
 * @Description
 */
//@Configuration
public class ShiroConfig {

    @Bean(name = "userRealm")
    public UserRealm customShiroRealm() {
        UserRealm realm = new UserRealm();
        return realm;
    }

    // SessionManager 实际未使用，但必须初始化，避免报错
//    @Bean("sessionManager")
//    public SessionManager sessionManager(RedisShiroSessionDAO redisShiroSessionDAO) {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        //设置session过期时间为1小时(单位：毫秒)，默认为30分钟
//        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);
//        sessionManager.setSessionValidationSchedulerEnabled(false);
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
//        sessionManager.setSessionIdCookieEnabled(false);
////
////        //如果开启redis缓存且renren.shiro.redis=true，则shiro session存到redis里
//        sessionManager.setSessionDAO(redisShiroSessionDAO);
//        return sessionManager;
//    }

    @Bean("securityManager")
    public SecurityManager securityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);

        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager, RedisTokenManager redisTokenManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        
        Map<String, Filter> filterMap = new HashMap<>();
        JwtFilter jwtFilter = new JwtFilter();
        jwtFilter.setTokenManager(redisTokenManager);
        filterMap.put("jwt", jwtFilter);
//        filterMap.put("authc", new AuthcFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");

        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        filterRuleMap.put("/user/validUser", "anon");
//        filterRuleMap.put("/**", "authc");
        filterRuleMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilterFactoryBean;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
