package com.momo.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momo.crud.bean.Loginer;
import com.momo.crud.bean.LoginerExample;
import com.momo.crud.bean.LoginerExample.Criteria;
import com.momo.crud.dao.LoginerMapper;

@Service
public class LoginService {

	@Autowired
	private LoginerMapper loginerMapper;

	public boolean checkLogin(Loginer loginer) {
		LoginerExample example = new LoginerExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andUserNameEqualTo(loginer.getUserName());
		Criteria.andUserPasswordEqualTo(loginer.getUserPassword());
		int count = loginerMapper.countByExample(example);
		return count==0;
	}

	public void addUser(Loginer loginer) {
		// TODO Auto-generated method stub
		loginerMapper.insertSelective(loginer);
	}
}
