package cn.linzs.app.common.exception;

import cn.linzs.app.common.dto.ReturnResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author linzs
 * @Date 2018-01-25 16:13
 * @Description
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 自定义业务异常处理
    @ExceptionHandler(value = BaseException.class)
    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        ReturnResult result = new ReturnResult(ReturnResult.OperationCode.EXCEPTION, e.getMessage());
        return result;
    }

    // 不可预料异常处理
    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
        logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
        ReturnResult result = new ReturnResult(ReturnResult.OperationCode.EXCEPTION, e.getLocalizedMessage());
        if(e instanceof org.apache.shiro.authz.AuthorizationException) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else if(res.getStatus() == HttpServletResponse.SC_OK) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
