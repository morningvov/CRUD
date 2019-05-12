package com.momo.crud.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.momo.crud.bean.Employee;
import com.momo.crud.bean.EmployeeExample;
import com.momo.crud.bean.EmployeeExample.Criteria;
import com.momo.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;
	/**
	 * 查询所有员工
	 * @return
	 */
	public List<Employee> getAll() {
		return employeeMapper.selectByExampleWithDept(null);
	}
	
	/**
	 * 保存员工
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 
	 * @param empName
	 * @return true:用户名可用 false:用户名不可用
	 */
	public boolean checkUser(String empName) {
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		// TODO Auto-generated method stub
		int count = employeeMapper.countByExample(example);
		return count==0;
	}

	/**
	 * 按照id查询员工信息并返回
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}

	/**
	 * 员工更新
	 * @param employee 
	 */
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	/**
	 * 员工删除
	 * @param id
	 */
	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		employeeMapper.deleteByPrimaryKey(id);
;	}

	/*
	 * 批量删除
	 */
	public void deleteBathch(List<Integer> ids) {
		EmployeeExample example = new EmployeeExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andEmpIdIn(ids);  //where id in (x,x,x);
		employeeMapper.deleteByExample(example);
	}

	
}
