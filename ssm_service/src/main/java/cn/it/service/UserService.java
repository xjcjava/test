package cn.it.service;

import cn.it.domain.Condition;
import cn.it.domain.PageBean;
import cn.it.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();
    User findById(Integer id);
    void addUser(User user);
    PageBean<User> findUserByPage(Condition condition,Integer currentPage);
    boolean login(User user);
    void delUserCheck(Integer [] ids);
    void delUserById(Integer uid);
    void updateUserById(User user);
}
