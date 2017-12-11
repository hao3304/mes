package com.yizit.mes.controller;

import com.yizit.mes.domain.User;
import com.yizit.mes.service.impl.UserServiceImpl;
import com.yizit.mes.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ModelAndView index(Model model) {
            model.addAttribute("title","用户管理");
           return new ModelAndView("user/list","userModel",model);
    }

    @GetMapping("/list.json")
    public Response list(
            @RequestParam(value = "page", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = new PageRequest(pageIndex -1, pageSize);
        Page<User> page = userService.ListUser(pageable);

        return new Response("0","",page.getTotalElements(), page.getContent());
    }

}
