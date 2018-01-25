package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author linzs
 * @Date 2017-12-04 9:42
 * @Description
 */
@RestController
public class MainController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping("/error")
    public ReturnResult error(HttpServletRequest request, HttpServletResponse response) {
        int status = response.getStatus();
        switch (status) {
            case ReturnResult.HttpCode._302: return new ReturnResult(ReturnResult.HttpCode._302, response.getHeader("Location"));
            case ReturnResult.HttpCode._404: return new ReturnResult(ReturnResult.HttpCode._404, response.getHeader("Can not find the page."));
            default: return new ReturnResult(ReturnResult.HttpCode._500, "Internal server error.");
        }
    }

    @RequestMapping("/401")
    public ReturnResult _403(HttpServletRequest request, HttpServletResponse response) {
        return new ReturnResult(ReturnResult.HttpCode._403, "未授权");
    }

}
