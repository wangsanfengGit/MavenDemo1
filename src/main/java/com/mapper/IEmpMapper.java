package com.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.po.Emp;
import com.po.PageBean;

@Service("EmpDAO")
public interface IEmpMapper {
  public int save(Emp emp);
  public int update(Emp emp);
  public int delById(Integer eid);
  public Emp findById(Integer eid);
  public List<Emp> findPageAll(PageBean pb);
  public int findMaxRows();
  //获取最大id
  public int findmaxId();
}
