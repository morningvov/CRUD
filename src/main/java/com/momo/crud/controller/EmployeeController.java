package com.momo.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.momo.crud.bean.Employee;
import com.momo.crud.bean.Msg;
import com.momo.crud.service.EmployeeService;
/**
 * 处理员工的CRUD
 * @author Rainely
 */
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 员工删除
	 * 单删与批量删合一
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmp(@PathVariable("ids") String ids) {
		if(ids.contains("-")) {
			List<Integer> list_ids=new ArrayList<>();
			String id_str[]=ids.split("-");
			//将id数组变成集合
			for (String string : id_str) {
				list_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBathch(list_ids);
		}else {
			Integer id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	/**
	 * 员工更新
	 * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
	 * 解决方案：
	 * 1、配置上HttpPutFormContentFilter
	 * 2、它的作用:将请求体中的数据解析包装成一个map
	 * 3、requeset被重新包装,request.getParamter()被重写，就会从自己封装的map中取数据
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	@ResponseBody
	public Msg updateEmp(Employee employee) {
		System.out.println("将要更新的数据："+employee);
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/**
	 * 根据id查询员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id) {
		Employee employee=employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * 校验用户名是否可用
	 */
	@ResponseBody
	@RequestMapping(value="/checkuser",method=RequestMethod.POST)
	public Msg checkUser(@RequestParam("empName")String empName) {
		//判断用户名是否是合法的表达式
		String regName="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
		//判断用户名是否已被注册
		if(!empName.matches(regName)) {
			return Msg.fail().add("val_msg","用户名必须是6-16位的数字和字母的组合或是2-5位的中文.");
		}
		boolean b=employeeService.checkUser(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("val_msg","用户名已被注册");
		}
	}
	
	
	/**
	 * 员工保存
	 * 1、支持JSP303校验
	 * 2、导入Hibernate-Validator
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()) {
			Map<String,Object> map=new HashMap<>();
			//校验失败,返回失败的信息,并在模态框上显示错误信息
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				System.out.println("错误字段名:"+fieldError.getField());
				System.out.println("错误信息:"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorField",map);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	
	/**
	 * 需要导入jackson包
	 * 查询员工数据(分页查询)
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn) {
		//不是分页查询，有一千条的数据,引入PageHelper分页插件
		//引入pageHelper分页插件
		//在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pn, 5);
		//startPage后面跟着的查询就是分页查询
		List<Employee> employees=employeeService.getAll();
		//使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行了
		//封装了详细地分页信息，包括有我们查询出来的数据，传入连续显示的页数
		PageInfo pageInfo=new PageInfo<>(employees, 5);
		return Msg.success().add("pageInfo", pageInfo);
	}
	
	
	
	
	/**
	 * 查询员工数据(分页查询)
	 * @return
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,
			Model model) {
		//不是分页查询，有一千条的数据,引入PageHelper分页插件
		//引入pageHelper分页插件
		//在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pn, 5);
		//startPage后面跟着的查询就是分页查询
		List<Employee> employees=employeeService.getAll();
		//使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行了
		//封装了详细地分页信息，包括有我们查询出来的数据，传入连续显示的页数
		PageInfo pageInfo=new PageInfo<>(employees, 5);
		model.addAttribute("pageInfo", pageInfo);
		return "list";
	}
}
