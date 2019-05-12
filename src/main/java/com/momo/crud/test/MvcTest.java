package com.momo.crud.test;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.github.pagehelper.PageInfo;
import com.momo.crud.bean.Employee;

/**
 * 使用spring测试模块提供的测试请求功能，测试crud请求的正确性
 * Spring4测试的时候，需要servlet 3.0的支持
 * @author Rainely
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {

	//传入springmvc的ioc
	@Autowired
	WebApplicationContext context;
	
	//虚拟mvc请求，获取到处理结果
	MockMvc mockMvc;
	
	@Before
	public void initMockMvc() {
	     mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception {
		//模拟请求拿到返回值
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "4"))
					.andReturn();
		//请求成功以后，请求域中会有pageInfo，可以取出数据进行验证
		MockHttpServletRequest request = result.getRequest();
		PageInfo pInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println("当前页码："+pInfo.getPageNum());
		System.out.println("总页码："+pInfo.getPages());
		System.out.println("总记录数："+pInfo.getTotal());
		System.out.println("在页面需要连续显示的页面:");
		int[] nums = pInfo.getNavigatepageNums();
		for (int i : nums) {
			System.out.print(" "+i);
		}
		System.out.println();
		//获取员工信息
		List<Employee> list = pInfo.getList();
		for (Employee employee : list) {
			System.out.println("id："+employee.getEmpId()+"===>name： "+employee.getEmpName());
		}
	}
}
