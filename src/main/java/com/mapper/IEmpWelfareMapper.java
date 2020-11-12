package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.EmpWelfare;
import com.po.Welfare;

@Service("EmpWelfareDAO")
public interface IEmpWelfareMapper {
    //保存员工福利
	public int save(EmpWelfare ewf);
	//根据员工编号查询福利
	public List<Welfare> findByEid(Integer eid);
	//根据员工编号删除福利
	public int delByEid(Integer eid);
}
