package cn.it.service.impl;

import cn.it.domain.Condition;
import cn.it.domain.PageBean;
import cn.it.domain.User;
import cn.it.mapper.UserMapper;
import cn.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public PageBean<User> findUserByPage(Condition condition,Integer currentPage) {

        PageBean<User> pb=new PageBean<User>();
        Integer totalCount = userMapper.findTotalCount(condition);

        Integer rows = 5;
        Integer totalPage = totalCount % rows == 0 ? (totalCount / rows) : (totalCount / rows) + 1;

        Integer start = (currentPage - 1) * rows;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("condition",condition);
        map.put("start",start);
        map.put("rows",rows);
        List<User> list = userMapper.findByPage(map);

        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        pb.setTotalCount(totalCount);
        pb.setList(list);

        return pb;

    }

    public boolean login(User user) {
        User login = userMapper.login(user);
        if (login != null){
            return true;
        }
        return false;
    }

    public void delUserCheck(Integer[] ids) {
        userMapper.delUserCheck(ids);
    }

    public void delUserById(Integer uid) {
        userMapper.delUserById(uid);
    }

    public void updateUserById(User user) {
        userMapper.updateUserById(user);
    }
}
