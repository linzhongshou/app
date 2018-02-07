package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Category;
import cn.linzs.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author linzs
 * @Date 2017-12-05 11:48
 * @Description
 */

@RestController
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/api/category/categorys")
    public ReturnResult getCategoryPage(@RequestParam(name = "currPage", defaultValue = "1") int currPage,
                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.findByPage(currPage, pageSize);
    }

    @RequestMapping("/category/all")
    public ReturnResult getAllCategory() {
        return categoryService.findAll();
    }

    @RequestMapping(value = "/api/category", method = RequestMethod.POST)
    public ReturnResult save(@Valid Category category, BindingResult bindingResult) {
        List<String> errorMessageList = parseBindingResult(bindingResult);
        if(errorMessageList == null) {
            return categoryService.save(category);
        } else {
            return new ReturnResult(ReturnResult.OperationCode.ERROR, errorMessageList);
        }
    }

    @RequestMapping(value = "/api/category/{id}", method = RequestMethod.DELETE)
    public ReturnResult save(@PathVariable("id") Long id) {
        return categoryService.delete(id);
    }
}
