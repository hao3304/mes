package com.yizit.mes.controller;

import com.yizit.mes.domain.User;
import com.yizit.mes.service.impl.UserServiceImpl;
import com.yizit.mes.util.ConstraintViolationExceptionHandler;
import com.yizit.mes.vo.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ModelAndView index(Model model) {
            model.addAttribute("title","用户管理");
           return new ModelAndView("user/index","userModel",model);
    }

    @GetMapping("/list.json")
    @ApiOperation(value = "用户列表查询",notes = "根据username查询")
    public ResponseEntity<Response> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
            @RequestParam(value = "limit", required = false, defaultValue = "10") int pageSize) {

        Pageable pageable = new PageRequest(pageIndex -1, pageSize);
        Page<User> page = userService.listUser(pageable);

        return ResponseEntity.ok().body(new Response("0","",page.getTotalElements(), page.getContent()));
    }

    @PostMapping
    @ApiOperation(value = "新增用户",notes = "新增用户")
    public ResponseEntity<Response> save(User user) {

        User t = userService.findByUserName(user.getUsername());
        if(t != null && user.getId() == null) {
            return ResponseEntity.ok().body(new Response("400","已经相同用户名！"));
        }

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response("400","处理失败！", ConstraintViolationExceptionHandler.getMessage(e)));
        }
        return ResponseEntity.ok().body(new Response("0","操作成功！", user));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户",notes = "根据id删除用户")
    public ResponseEntity<Response>  remove(@PathVariable("id") Long id) {

        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response("400","处理失败！", e.getMessage()));
        }
        return ResponseEntity.ok().body(new Response("0","删除成功！"));
    }

}
