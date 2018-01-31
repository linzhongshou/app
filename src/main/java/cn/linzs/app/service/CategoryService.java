package cn.linzs.app.service;

import cn.linzs.app.common.dto.ReturnResult;
import cn.linzs.app.domain.Category;
import cn.linzs.app.repo.IArticleRepo;
import cn.linzs.app.repo.ICategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private IArticleRepo articleRepo;

    public ReturnResult findByPage(int currPage, int pageSize) {
        Pageable pageable = new PageRequest(currPage - 1, pageSize);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, categoryRepo.findAll(pageable));
    }

    public ReturnResult findAll() {
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, categoryRepo.findAll());
    }

    public ReturnResult save(Category category) {
        if (categoryRepo.countByNameAndIdNot(category.getName(), category.getId() == null ? -1 : category.getId()) <= 0) {
            if (category.getId() == null) {
                category.setCreateDate(new Date());
                category.setUpdateDate(new Date());
            } else {
                category.setUpdateDate(new Date());
            }
        } else {
            List<String> errorMessageList = new ArrayList<>();
            errorMessageList.add("已存在相同名称[" + category.getName() + "]");
            return new ReturnResult(ReturnResult.OperationCode.ERROR, errorMessageList);
        }

        categoryRepo.save(category);
        return new ReturnResult(ReturnResult.OperationCode.SUCCESS, null);
    }

    public ReturnResult delete(Long id) {
        if(id != null) {
            if(articleRepo.countByCategoryId(id) > 0) {
                return new ReturnResult(ReturnResult.OperationCode.SUCCESS, "目录下已有博客文章关联，无法删除");
            } else {
                categoryRepo.delete(id);
                return new ReturnResult(ReturnResult.OperationCode.SUCCESS, null);
            }
        } else {
            return new ReturnResult(ReturnResult.OperationCode.ERROR, "删除ID不能为空");
        }
    }
}
