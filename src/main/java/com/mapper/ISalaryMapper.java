package com.mapper;

import org.springframework.stereotype.Service;

import com.po.Salary;

@Service("SalaryDAO")
public interface ISalaryMapper {
    //保存薪资
	public int save(Salary sa);
	//根据员工编号，更新薪资
	public int updateByEid(Salary sa);
	//根据员工编号，删除薪资
	public int delByEid(Integer eid);
	//根据员工编号，查询薪资
	public Salary findSalaryByEid(Integer eid);
}
