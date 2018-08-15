package com.scushare.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.mapper.ManagerInfoMapper;
import com.scushare.service.ManagerInfoService;

import scu.pojo.Manager;

@Service
public class ManagerInfoServiceImpl implements ManagerInfoService {

	@Autowired
	private ManagerInfoMapper managerInfoMapper=null;
	
	public List<Manager> find(String Mname) {
		
		return managerInfoMapper.find(Mname);
	}

}
