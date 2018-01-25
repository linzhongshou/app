package cn.linzs.app.common.utils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;

/** 
* @ClassName: SpringContextUtil 
* @Description: 在Spring无法注入的情况下，可用此工具类获取Spring容器管理的对象
* @author Lin 
* @date 2016年6月27日 下午12:31:36 
*  
*/
public class SpringContextUtil implements DisposableBean {
	 
    private static ApplicationContext applicationContext = null;
 
    private static Logger logger = Logger.getLogger(SpringContextUtil.class);
 
    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        logger.debug("从SpringContextUtil中取出Bean:" + name);
        return (T) applicationContext.getBean(name);
    }
 
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }
 
    /**
     * 清除SpringContextUtil中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        logger.debug("清除SpringContextUtil中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }
 
    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext != null) {
            logger.warn("SpringContextUtil中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringContextUtil.applicationContext);
        }
 
        SpringContextUtil.applicationContext = applicationContext; // NOSONAR
    }
 
    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextUtil.clearHolder();
    }
 
}
