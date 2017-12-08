package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.utils.JwtUtil;
import cn.linzs.app.domain.User;
import cn.linzs.app.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public ReturnResult getUser(@PathVariable("id") Long id) {
        ReturnResult result;
        try {
            User user = userService.findUserById(id);
            user.setPassword(null);

            result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, user);
        } catch (Exception e) {
            result = new ReturnResult(ReturnResult.OperationCode.EXCEPTION, e.getLocalizedMessage());
        }

        return result;
    }

    @RequestMapping(value = "/api/user/userinfo", method = RequestMethod.GET)
    public ReturnResult getUserInfo(HttpServletRequest request) {
        ReturnResult result;
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            User user = userService.findUserById(Long.valueOf(claims.get("userId").toString()));
            user.setPassword(null);

            result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, user);
        } catch (Exception e) {
            result = new ReturnResult(ReturnResult.OperationCode.EXCEPTION, e.getLocalizedMessage());
        }

        return result;
    }

    @RequestMapping(value = "/user/validUser", method = RequestMethod.POST)
    public ReturnResult validUser(@RequestParam(value = "account", required = false) String account,
                                  @RequestParam(value = "password", required = false) String password) {
        ReturnResult result;

        if(account == null || password == null) {
            result = new ReturnResult(ReturnResult.OperationCode.ERROR, "Account or password of user can not empty.");
        } else {
            User user = userService.findByAccount(account);
            if(user == null || !user.getPassword().equals(password)) {
                result = new ReturnResult(ReturnResult.OperationCode.ERROR, "Account or password error.");
            } else {
                Map<String, Object> claims = new HashMap<>();
                claims.put("userId", user.getId());
                String token = JwtUtil.generateToken(claims);
                result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, token);
            }
        }

        return result;
    }
}
