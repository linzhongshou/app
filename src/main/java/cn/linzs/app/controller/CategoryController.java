package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author linzs
 * @Date 2017-12-05 11:48
 * @Description
 */

@RestController
@RequestMapping(value = "/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categorys")
    public ReturnResult findByPage(@RequestParam(name = "currPage", defaultValue = "0") int currPage,
                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return categoryService.findByPage(currPage, pageSize);
    }
}
