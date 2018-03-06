package cn.linzs.app.service;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Article;
import cn.linzs.app.repo.IArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    public ReturnResult findByPage(Long categroyId, int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage - 1, pageSize);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, articleRepo.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Long> categoryIdPath = root.get("categoryId");
                List<Predicate> predicateList = new ArrayList<>();

                if(categroyId != null) {
                    predicateList.add(criteriaBuilder.equal(categoryIdPath, categroyId));
                }

                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return criteriaQuery.getRestriction();
            }
        }, pageable));
    }

    public ReturnResult findById(Long id) {
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, articleRepo.findOne(id));
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
