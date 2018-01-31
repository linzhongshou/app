package cn.linzs.app.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author linzs
 * @Date 2018-01-31 12:01
 * @Description
 */
public class BaseController {

    protected List<String> parseBindingResult(BindingResult bindingResult) {
        List<String> errorMessageList = null;
        if(bindingResult.hasErrors()) {
            errorMessageList = new ArrayList<>();
            for(ObjectError objectError : bindingResult.getAllErrors()) {
                errorMessageList.add(objectError.getDefaultMessage());
            }
        }

        return errorMessageList;
    }
}
