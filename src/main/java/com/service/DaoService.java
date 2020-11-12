package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mapper.*;

@Service("DaoService")
public class DaoService {
	@Resource(name="DepDAO")
	private IDepMapper depmapper;
	@Resource(name="EmpDAO")
	private IEmpMapper empmapper;
	@Resource(name="EmpWelfareDAO")
	private IEmpWelfareMapper empwelfaremapper;
	@Resource(name="SalaryDAO")
	private ISalaryMapper salarymapper;
	@Resource(name="WelfareDAO")
	private IWelfareMapper welfaremapper;
	public IDepMapper getDepmapper() {
		return depmapper;
	}
	public void setDepmapper(IDepMapper depmapper) {
		this.depmapper = depmapper;
	}
	public IEmpMapper getEmpmapper() {
		return empmapper;
	}
	public void setEmpmapper(IEmpMapper empmapper) {
		this.empmapper = empmapper;
	}
	public IEmpWelfareMapper getEmpwelfaremapper() {
		return empwelfaremapper;
	}
	public void setEmpwelfaremapper(IEmpWelfareMapper empwelfaremapper) {
		this.empwelfaremapper = empwelfaremapper;
	}
	public ISalaryMapper getSalarymapper() {
		return salarymapper;
	}
	public void setSalarymapper(ISalaryMapper salarymapper) {
		this.salarymapper = salarymapper;
	}
	public IWelfareMapper getWelfaremapper() {
		return welfaremapper;
	}
	public void setWelfaremapper(IWelfareMapper welfaremapper) {
		this.welfaremapper = welfaremapper;
	}
	

}
