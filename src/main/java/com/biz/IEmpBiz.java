package com.biz;

import java.util.List;

import com.po.Emp;
import com.po.PageBean;

public interface IEmpBiz {
	  public boolean save(Emp emp);
	  public boolean update(Emp emp);
	  public boolean delById(Integer eid);
	  public Emp findById(Integer eid);
	  public List<Emp> findPageAll(PageBean pb);
	  public int findMaxRows();
}
