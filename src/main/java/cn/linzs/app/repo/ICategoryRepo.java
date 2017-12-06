package cn.linzs.app.repo;

import cn.linzs.app.domain.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by linzs on 2017-12-05 11:46
 *
 * @Description
 */
@Repository
public interface ICategoryRepo extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
