package cn.linzs.app.controller;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Article;
import cn.linzs.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author linzs
 * @Date 2018-01-31 9:35
 * @Description
 */
@RestController
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article/articles")
    public ReturnResult getArticlePage(@RequestParam(required = false) Long categoryId,
                                       @RequestParam(required = false) String searchContent,
                                       @RequestParam(name = "currPage", defaultValue = "1") int currPage,
                                       @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return articleService.findByPage(categoryId, searchContent, currPage, pageSize);
    }

    @RequestMapping(value = "api/article/{id}", method = RequestMethod.GET)
    public ReturnResult getArticle(@PathVariable Long id) {
        if(id == null || id <= 0) {
            return new ReturnResult(ReturnResult.OperationCode.ERROR, "param of id is incorrect.");
        }
        return articleService.findById(id);
    }

    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public ReturnResult getArticleForFrontend(@PathVariable Long id) {
        if(id == null || id <= 0) {
            return new ReturnResult(ReturnResult.OperationCode.ERROR, "param of id is incorrect.");
        }
        return articleService.getArticle(id);
    }

    @RequestMapping(value = "/api/article", method = RequestMethod.POST)
    public ReturnResult save(@Valid Article article, BindingResult bindingResult) {
        List<String> errorMessageList = parseBindingResult(bindingResult);
        if(errorMessageList == null) {
            return articleService.save(article);
        } else {
            return new ReturnResult(ReturnResult.OperationCode.ERROR, errorMessageList);
        }
    }

    @RequestMapping(value = "/api/article/{id}", method = RequestMethod.DELETE)
    public ReturnResult delete(@PathVariable("id") Long id) {
        return articleService.delete(id);
    }

}
