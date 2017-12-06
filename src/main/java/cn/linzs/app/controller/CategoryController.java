package cn.linzs.app.controller;

import cn.linzs.app.domain.Category;
import cn.linzs.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author linzs
 * @Date 2017-12-05 11:48
 * @Description
 */

@RestController
@RequestMapping(value = "/api/category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public List<Category> page() {
        return categoryService.page();
    }
}
