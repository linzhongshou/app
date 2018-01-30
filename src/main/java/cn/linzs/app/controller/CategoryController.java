package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Category;
import cn.linzs.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author linzs
 * @Date 2017-12-05 11:48
 * @Description
 */

@RestController
@RequestMapping(value = "/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/category/categorys")
    public ReturnResult findByPage(@RequestParam(name = "currPage", defaultValue = "0") int currPage,
                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.findByPage(currPage, pageSize);
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ReturnResult save(@Valid Category category, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            return categoryService.save(category);
        } else {
            List<String> errorMessageList = new ArrayList<>();
            for(ObjectError objectError : bindingResult.getAllErrors()) {
                errorMessageList.add(objectError.getDefaultMessage());
            }
            return new ReturnResult(ReturnResult.OperationCode.ERROR, errorMessageList);
        }
    }

    @RequestMapping(value = "/category", method = RequestMethod.DELETE)
    public ReturnResult save(@RequestParam Long id) {
        return categoryService.delete(id);
    }
}
