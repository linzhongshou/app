package cn.linzs.app.service;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.repo.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ReturnResult findByPage(int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage, pageSize);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, categoryRepo.findAll(pageable));
    }

}
