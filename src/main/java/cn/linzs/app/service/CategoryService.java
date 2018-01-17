package cn.linzs.app.service;

import cn.linzs.app.domain.Category;
import cn.linzs.app.repo.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Page<Category> findByPage(int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage, pageSize);
        return categoryRepo.findAll(pageable);
    }

}