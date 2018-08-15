package com.scushare.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import scu.pojo.Manager;

public interface ManagerInfoService {

	List<Manager> find(String Mname);
}
