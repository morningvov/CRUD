package com.momo.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.momo.crud.bean.Department;
import com.momo.crud.bean.Employee;
import com.momo.crud.dao.DepartmentMapper;
import com.momo.crud.dao.EmployeeMapper;

/**
 * 测试dao层的工作
 * @author Rainely
 * 推荐 spring的项目就可以使用spring的单元测试，可以自动注入我们需要的组件
 * 1、导入spring Test的模块
 * 2、使用@ContextConfiguration指定Spring配置文件的位置
 * 3、直接autowired要使用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD() {
		/*//1、获取ioc容器
		ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
		//2、从ioc容器中获取mapper
		DepartmentMapper bean = ctx.getBean(DepartmentMapper.class);*/
		System.out.println(departmentMapper);
		
		//插入几个部门
		/*departmentMapper.insertSelective(new Department(null, "开发部"));
		departmentMapper.insertSelective(new Department(null, "测试部"));*/
		
		//生成员工数据，测试员工插入
		//employeeMapper.insertSelective(new Employee(null, "Tom", 0, "Tom@momo.com", 1));
		
		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
		for(int i=0;i<1000;i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null,uid,i%2==0?0:1, uid+"@momo.com",i%2==0?1:2));
		}
		System.out.println("批量操作完成！");
	}
}
