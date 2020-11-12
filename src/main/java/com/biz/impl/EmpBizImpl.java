package com.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.IEmpBiz;
import com.po.Emp;
import com.po.EmpWelfare;
import com.po.PageBean;
import com.po.Salary;
import com.po.Welfare;
import com.service.DaoService;
@Service("EmpBiz")
@Transactional
public class EmpBizImpl implements IEmpBiz {
	@Resource(name="DaoService")
    private DaoService daoService;
	 
	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public boolean save(Emp emp) {
		int code=daoService.getEmpmapper().save(emp);
		if(code>0){
			//获取刚才保存的员工Id
			Integer eid=daoService.getEmpmapper().findmaxId();
			/****保存薪资*****/
			Salary sa=new Salary(eid,emp.getEmoney());
			daoService.getSalarymapper().save(sa);
			/****保存薪资end*****/
			/****获取员工福利数组*****/
			String[] wids=emp.getWids();
			if(wids!=null&&wids.length>0){
				for(int i=0;i<wids.length;i++){
					EmpWelfare ewf=new EmpWelfare(eid,new Integer(wids[i]));
					daoService.getEmpwelfaremapper().save(ewf);
				}
			}
			/****获取员工福利数组end*****/
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Emp emp) {
		int code=daoService.getEmpmapper().update(emp);
		if(code>0){
			/**更新薪资**/
			 //获取原来的薪资
			Salary oldsa=daoService.getSalarymapper().findSalaryByEid(emp.getEid());
			if(oldsa!=null&&oldsa.getEmoney()!=null){
				oldsa.setEmoney(emp.getEmoney());//薪资修改
				daoService.getSalarymapper().updateByEid(oldsa);
			}else{
				Salary sa=new Salary(emp.getEid(),emp.getEmoney());
				daoService.getSalarymapper().save(sa);
			}
			/**更新薪资end**/
			/**更新员工福利表***/
			  //获取原来的员工福利
			List<Welfare>  lswf=daoService.getEmpwelfaremapper().findByEid(emp.getEid());
			if(lswf!=null&&lswf.size()>0){
				//删除原来员工福利
				daoService.getEmpwelfaremapper().delByEid(emp.getEid());
			}
			//添加更新
			String[] wids=emp.getWids();
			if(wids!=null&&wids.length>0){
				for(int i=0;i<wids.length;i++){
					EmpWelfare ewf=new EmpWelfare(emp.getEid(),new Integer(wids[i]));
					daoService.getEmpwelfaremapper().save(ewf);
				}
			}
			/**更新员工福利表end***/
			return true;
		}
		return false;
	}

	@Override
	public boolean delById(Integer eid) {
		//删除子表
		daoService.getSalarymapper().delByEid(eid);
		daoService.getEmpwelfaremapper().delByEid(eid);
		//删除员工表
		int code=daoService.getEmpmapper().delById(eid);
		if(code>0){
			return true;
		}
		return false;
	}

	@Override
	public Emp findById(Integer eid) {
		//获取员工对象
		Emp oldemp=daoService.getEmpmapper().findById(eid);
		/******获取薪资*******/
		Salary oldsa=daoService.getSalarymapper().findSalaryByEid(eid);
		if(oldsa!=null&&oldsa.getEmoney()!=null){
			oldemp.setEmoney(oldsa.getEmoney());
		}
		/******获取薪资end*******/
		/******获取福利*******/
		 //获取原来的员工福利
		List<Welfare>  lswf=daoService.getEmpwelfaremapper().findByEid(oldemp.getEid());
		if(lswf!=null&&lswf.size()>0){
			//创建福利数组
			String[] wids=new String[lswf.size()];
			for(int i=0;i<lswf.size();i++){
				Welfare wf=lswf.get(i);
				wids[i]=wf.getWid().toString();
			}
			oldemp.setWids(wids);
		}
		/******获取福利end*******/
		oldemp.setLswf(lswf);//详细页面
		return oldemp;
	}

	@Override
	public List<Emp> findPageAll(PageBean pb) {
		if(pb!=null){
			return daoService.getEmpmapper().findPageAll(pb);
		}
		return null;
	}

	@Override
	public int findMaxRows() {
		// TODO Auto-generated method stub
		return daoService.getEmpmapper().findMaxRows();
	}

}
