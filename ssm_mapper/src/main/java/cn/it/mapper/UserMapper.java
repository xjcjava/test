package cn.it.mapper;

import cn.it.domain.Condition;
import cn.it.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    List<User> findAll();
    User findById(Integer id);
    void addUser(User user);
    User login(User user);
    Integer findTotalCount(Condition condition);
    List<User>findByPage(Map<String,Object> map);
    void delUserById(Integer uid);
    void delUserCheck(Integer [] ids);
    void updateUserById(User user);
}
