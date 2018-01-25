package cn.linzs.app.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Author linzs
 * @Date 2017-12-18 17:42
 * @Description
 */
public class ShiroUtil {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
