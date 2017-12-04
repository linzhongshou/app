package cn.linzs.app.repo;

import cn.linzs.app.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByAccount(String account);
}
