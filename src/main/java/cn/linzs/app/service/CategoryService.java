package cn.linzs.app.service;

import cn.linzs.app.domain.Category;
import cn.linzs.app.repo.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author linzs
 * @Date 2017-12-05 11:48
 * @Description
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    private ICategoryRepo categoryRepo;

    public List<Category> page() {
        return (List<Category>) categoryRepo.findAll();
    }

}
