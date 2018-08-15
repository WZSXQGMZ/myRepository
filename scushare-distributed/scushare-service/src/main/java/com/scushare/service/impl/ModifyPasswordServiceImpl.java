package com.scushare.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.mapper.ModifyPasswordMapper;
import com.scushare.service.ModifyPasswordService;
import com.scushare.utils.PwdUtil;
@Service
public class ModifyPasswordServiceImpl implements ModifyPasswordService {
	@Autowired
	ModifyPasswordMapper modifyPasswordMapper =null;
	@Autowired
	private PwdUtil pUtil;
	public boolean modify(String mpassword, String mname) {
		int num = modifyPasswordMapper.modifyByMname(mpassword,mname);
		if (num==1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean modifyPassWord(HttpServletRequest request) {
		//manager_name暂未传递与获得
		String mname=(String)request.getParameter("manager_name");
		String password=(String)request.getParameter("newpwd");	
		String mpassword=pUtil.encryptPwd(password);
		return modify(mpassword,mname);
	}

}
