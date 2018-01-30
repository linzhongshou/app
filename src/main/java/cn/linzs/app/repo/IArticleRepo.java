package cn.linzs.app.repo;

import cn.linzs.app.domain.Article;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by linzs on 2018-01-30 17:00
 *
 * @Description
 */
@Repository
public interface IArticleRepo extends PagingAndSortingRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    int countByCategoryId(Long categoryId);
}
