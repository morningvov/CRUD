package com.momo.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.momo.crud.bean.Loginer;
import com.momo.crud.bean.Msg;
import com.momo.crud.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 用户登录
	 * @param loginer
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Msg login(Loginer loginer) {
		boolean flag=loginService.checkLogin(loginer);
		if(flag) {
			return Msg.fail().add("msg","用户名或者密码错误");
		}else {
			return Msg.success().add("msg","登录成功");
		}
	}
	
	/*
	 * 页面跳转
	 */
	@RequestMapping(value="/jump",method=RequestMethod.GET)
	public String jump() {
		return "list";
	}
	
	/*
	 * 页面跳转,注册
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	
	/**
	 * 注册添加用户，并进行后台校验
	 * @param loginer
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Msg addUser(@Valid Loginer loginer,BindingResult result) {
		
		if(result.hasErrors()) {
			Map<String,Object> map=new HashMap<>();
			//校验失败,返回失败的信息
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误字段名:"+fieldError.getField());
				System.out.println("错误信息:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorField",map);
		}else {
			loginService.addUser(loginer);
			return Msg.success().add("msg","注册成功");
		}
	}
	
}
