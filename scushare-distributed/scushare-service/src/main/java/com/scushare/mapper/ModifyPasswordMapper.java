package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface ModifyPasswordMapper {

	@Update("update manager set manager_password =#{mpassword} where manager_name=#{mname}")
	int modifyByMname(@Param("mpassword") String mpassword,@Param("mname") String mname);
}
