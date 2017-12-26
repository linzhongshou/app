package cn.linzs.app.common.filter;

import cn.linzs.app.common.dto.ReturnResult;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author linzs
 * @Date 2017-12-19 11:43
 * @Description
 */
public class AuthcFilter extends AuthorizationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
//        Subject subject = getSubject(servletRequest, servletResponse);
//        if (null != subject.getPrincipals()) {
//            return true;
//        }
//        return false;
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isAjax(request)) {
            response.setStatus(ReturnResult.HttpCode._403);
        } else {
            String unauthorizedUrl = getUnauthorizedUrl();
            response.setStatus(ReturnResult.HttpCode._403);
            response.sendRedirect(unauthorizedUrl);
        }

        return false;
    }

    private boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("x-requested-with");
        if (null != header && "XMLHttpRequest".endsWith(header)) {
            return true;
        }
        return false;
    }
}
