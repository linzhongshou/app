package cn.linzs.app.service;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Article;
import cn.linzs.app.repo.IArticleRepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

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

    public ReturnResult findByPage(Long categroyId, String searchContent, int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage - 1, pageSize);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, articleRepo.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Long> categoryIdPath = root.get("categoryId");
                Path<String> titlePath = root.get("title");
                Path<String> contentPath = root.get("content");
                List<Predicate> predicateList = new ArrayList<>();

                if(categroyId != null) {
                    predicateList.add(criteriaBuilder.equal(categoryIdPath, categroyId));
                }
                if(!StringUtils.isEmpty(searchContent)) {
                    String content = "%" + searchContent + "%";
                    predicateList.add(criteriaBuilder.or(
                            criteriaBuilder.like(titlePath, content),
                            criteriaBuilder.like(contentPath, content) ));
                }

                criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return criteriaQuery.getRestriction();
            }
        }, pageable));
    }

    public ReturnResult findById(Long id) {
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, articleRepo.findOne(id));
    }

    public ReturnResult getArticle(Long id) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("article", articleRepo.findOne(id));
        dataMap.put("prev", articleRepo.findOne(id - 1));
        dataMap.put("next", articleRepo.findOne(id + 1));
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, dataMap);
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
