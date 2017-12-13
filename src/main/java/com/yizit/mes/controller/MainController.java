package com.yizit.mes.controller;

import com.yizit.mes.domain.Authority;
import com.yizit.mes.domain.User;
import com.yizit.mes.security.SecurityUser;
import com.yizit.mes.security.SecurityUtrl;
import com.yizit.mes.service.impl.AuthorityServiceImpl;
import com.yizit.mes.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthorityServiceImpl authorityService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
       //根据用户权限获取菜单
        SecurityUser securityUser = SecurityUtrl.getUser();

        List<Authority> authorities;
        if(SecurityUtrl.isRoot()) {
            authorities = authorityService.listAuthority();
        }else{
            User user = userService.findByUserName(securityUser.getUsername());
            authorities = user.getRole().getAuthorityList();
        }

        List<Authority> menu = new ArrayList<>();
        for(Authority authority : authorities) {
            if(authority.getType() == Authority.Type.Menu && authority.getPid() == 0) {
                menu.add(authority);
            }
        }

        model.addAttribute("menu",menu);
        model.addAttribute("title","生产追溯管理系统");

        return "layout";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登陆失败，账号或者密码错误！");
        return "login";
    }
}
