package cn.linzs.app.service;

import cn.linzs.app.domain.User;
import cn.linzs.app.repo.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepo userRepo;

    public User findUserById(Long userId) {
        return userRepo.findOne(userId);
    }

    public User findByAccount(String account) {
        return userRepo.findByAccount(account);
    }
}
