package cn.linzs.app.service;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Article;
import cn.linzs.app.repo.IArticleRepo;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @Author linzs
 * @Date 2018-01-31 9:33
 * @Description
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    private IArticleRepo articleRepo;

    public ReturnResult findByPage(int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage - 1, pageSize);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, articleRepo.findAll(pageable));
    }

    public ReturnResult save(Article article) {
        Date now = new Date();
        if(article.getId() == null) {
            article.setCreateDate(now);
            article.setUpdateDate(now);
        } else {
            article.setUpdateDate(now);
        }
        articleRepo.save(article);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, null);
    }

    public ReturnResult delete(Long id) {
        articleRepo.delete(id);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, null);
    }

}
