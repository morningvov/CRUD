package com.momo.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momo.crud.bean.Department;
import com.momo.crud.bean.Msg;
import com.momo.crud.service.DepartmentService;

/**
 * 处理与部门相关的请求
 * @author Rainely
 *
 */
@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	/**
	 * 返回所有部门信息
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts() {
		List<Department> list=departmentService.getDepts();
		return Msg.success().add("depts", list);
	}
	
}
