package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.utils.JwtUtil;
import cn.linzs.app.domain.User;
import cn.linzs.app.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/validUser", method = RequestMethod.POST)
    public ReturnResult validUser(@RequestParam(value = "account", required = false) String account,
                                  @RequestParam(value = "password", required = false) String password,
                                  HttpServletResponse response) {
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
