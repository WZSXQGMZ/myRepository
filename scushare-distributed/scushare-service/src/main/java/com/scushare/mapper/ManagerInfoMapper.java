package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import scu.pojo.Manager;

public interface ManagerInfoMapper {

	@Select("select manager_id as managerId, manager_name as managerName from manager where manager_name like CONCAT('%',#{Mname},'%')")
	List<Manager> find(@Param("Mname") String name);
}
