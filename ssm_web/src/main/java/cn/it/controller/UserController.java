package cn.it.controller;

import cn.it.domain.Condition;
import cn.it.domain.PageBean;
import cn.it.domain.User;
import cn.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public String findAll(Model model, HttpSession session) {
        Object user = session.getAttribute("user");
        if (user==null){
            return "login";
        }
        List<User> userList = userService.findAll();
        model.addAttribute("userList",userList);
        return "list";
    }

    @RequestMapping("/addUser")
    public String addUser(User user) {
        userService.addUser(user);
        return "add";
    }

    @RequestMapping("/update")
    public String update(Integer uid,Model model){
        User user = userService.findById(uid);
        model.addAttribute("user",user);
        return "update";
    }

    @RequestMapping("/findUserByPage")
    public String findUserByPage(Condition condition, Integer currentPage, Model model, HttpSession session){
        Object user = session.getAttribute("user");
        if (user==null){
            return "login";
        }
        if (currentPage==null){
            currentPage =1;
        }
        PageBean<User> pb = userService.findUserByPage(condition, currentPage);
        model.addAttribute("pb",pb);
        model.addAttribute("condition",condition);

        return "list";
    }

    @RequestMapping("/login")
    public String login(User user, String verifycode, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (verifycode == null || !verifycode.equalsIgnoreCase(checkcode_server) || verifycode == ""){
            request.setAttribute("login_msg","验证码输入有误!");
            return "login";
        }else {
            boolean flag = userService.login(user);
            if (flag){
                session.setAttribute("user",user);
                try {
                    response.sendRedirect(request.getContextPath()+"/index.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                request.setAttribute("login_msg","用户名或密码错误!");
                return "login";
            }
        }
        return "login";
    }

    @RequestMapping("/delUserById")
    public void delUserById(Integer uid,HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.delUserById(uid);
        response.sendRedirect(request.getContextPath()+"/user/findByPage");
    }

    @RequestMapping("/delUserCheck")
    public void delUserCheck(Integer[] uid,HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.delUserCheck(uid);
        response.sendRedirect(request.getContextPath()+"/user/findUserByPage");

    }

    @RequestMapping("/updateUserById")
    public void updateUserById(User user,HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.updateUserById(user);
        response.sendRedirect(request.getContextPath()+"/user/findUserByPage");
    }


}
