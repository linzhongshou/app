package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
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
public class MainController {

    @RequestMapping("/404")
    public ReturnResult http404() {
        return new ReturnResult(ReturnResult.HttpCode._404, "Can not find the page.");
    }

   /* @RequestMapping("/error")
    public ReturnResult error(HttpServletRequest request, HttpServletResponse response) {
        request.getHeader("status");
        return new ReturnResult(ReturnResult.HttpCode._404, "Can not find the page.");
    }*/

}
