package com.yizit.mes.controller;

import com.yizit.mes.domain.Authority;
import com.yizit.mes.domain.User;
import com.yizit.mes.service.impl.UserServiceImpl;
import com.yizit.mes.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 获取菜单
 * User: 李江峰
 * Date: 2017-12-13
 * Time: 14:07
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("{id}")
    @ResponseBody
    public List<Authority> one(@PathVariable("id")Long id){
        Object principal =  SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String username =((UserDetails)principal).getUsername();
        User user = userService.findByUserName(username);
        List<Authority> authorities = user.getRole().getAuthorityList();
        List<Authority> menu = new ArrayList<>();
        for(Authority authority : authorities) {
            if(authority.getType() == Authority.Type.Menu && authority.getPid() == id) {
                menu.add(authority);
            }
        }
        return menu;
    }
}
