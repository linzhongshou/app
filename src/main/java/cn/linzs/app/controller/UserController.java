package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.common.utils.token.ITokenManager;
import cn.linzs.app.common.utils.token.model.TokenModel;
import cn.linzs.app.domain.User;
import cn.linzs.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ITokenManager tokenManager;

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
            TokenModel tokenModel = (TokenModel) request.getAttribute("tokenModel");
            User user = userService.findUserById(Long.valueOf(tokenModel.getDataMap().get("userId").toString()));
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
                String token = tokenManager.generateToken(claims).getToken();
                result = new ReturnResult(ReturnResult.OperationCode.SUCCESS, token);
            }
        }

        return result;
    }
}
