package com.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.service.BizService;
import com.util.AjaxUtils;
@Controller
public class EmpAction implements IAction {
	@Resource(name="BizService")
    private BizService bizService;
    
	public BizService getBizService() {
		return bizService;
	}

	public void setBizService(BizService bizService) {
		this.bizService = bizService;
	}

	@Override
	@RequestMapping(value="save_emp.do")
	public String save(HttpServletRequest request, HttpServletResponse response, Emp emp) {
		String realpath = request.getRealPath("/");
		/***************** 上传文件 ************************************/
		// 获取上传照片的对象
		MultipartFile multipartFile = emp.getPic();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// 获取上传的文件名称
			String fname = multipartFile.getOriginalFilename();
			// 更名
			if (fname.lastIndexOf(".") != -1) {// 存在后缀
				// 获取后缀名
				String ext = fname.substring(fname.lastIndexOf("."));

				// 判断后缀是否为jpg格式
				if (ext.equalsIgnoreCase(".jpg")) {
					// 改名
					String newfname = new Date().getTime() + ext;
					// 创建文件对象，指定上传文件的路径
					File destFile = new File(realpath + "/uppic/" + newfname);
					// 上传
					try {
						FileUtils.copyInputStreamToFile(
								multipartFile.getInputStream(), destFile);
						emp.setPhoto(newfname);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		/****************************************************************************/
		boolean flag=bizService.getEmpBiz().save(emp);
		if(flag){
			AjaxUtils.printString(response, 1+"");
		}else{
			AjaxUtils.printString(response, 0+"");
		}
		return null;
	}

	@Override
	@RequestMapping(value="update_emp.do")
	public String update(HttpServletRequest request, HttpServletResponse response, Emp emp) {
		String realpath = request.getRealPath("/");
		//获取原来的员工照片
		String oldphoto=bizService.getEmpBiz().findById(emp.getEid()).getPhoto();
		
		/***************** 上传文件 ************************************/
		// 获取上传照片的对象
		MultipartFile multipartFile = emp.getPic();
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// 获取上传的文件名称
			String fname = multipartFile.getOriginalFilename();
			// 更名
			if (fname.lastIndexOf(".") != -1) {// 存在后缀
				// 获取后缀名
				String ext = fname.substring(fname.lastIndexOf("."));

				// 判断后缀是否为jpg格式
				if (ext.equalsIgnoreCase(".jpg")) {
					// 改名
					String newfname = new Date().getTime() + ext;
					// 创建文件对象，指定上传文件的路径
					File destFile = new File(realpath + "/uppic/" + newfname);
					// 上传
					try {
						FileUtils.copyInputStreamToFile(
								multipartFile.getInputStream(), destFile);
						emp.setPhoto(newfname);
						
						//删除原来的照片
						File oldfile=new File(realpath+"/uppic/"+oldphoto);
						if(oldfile.exists()&&!oldphoto.equalsIgnoreCase("default.jpg")){
							oldfile.delete();//删除
							
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			emp.setPhoto(oldphoto);//在没有更改员工照片时，设置原来员工的照片为新照片
		}
		/****************************************************************************/
		boolean flag=bizService.getEmpBiz().update(emp);
		if(flag){
			AjaxUtils.printString(response, 1+"");
		}else{
			AjaxUtils.printString(response, 0+"");
		}
		return null;
	}

	@Override
	@RequestMapping(value="delById_emp.do")
	public String delById(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		boolean flag=bizService.getEmpBiz().delById(eid);
		if(flag){
			AjaxUtils.printString(response, 1+"");
		}else{
			AjaxUtils.printString(response, 0+"");
		}
		return null;
	}

	@Override
	@RequestMapping(value="findById_emp.do")
	public String findById(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		Emp oldemp=bizService.getEmpBiz().findById(eid);
		PropertyFilter propertyFilter=AjaxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjaxUtils.printString(response, jsonstr);
		return null;
	}

	@Override
	@RequestMapping(value="findDetail_emp.do")
	public String findDetail(HttpServletRequest request, HttpServletResponse response, Integer eid) {
		Emp oldemp=bizService.getEmpBiz().findById(eid);
		PropertyFilter propertyFilter=AjaxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(oldemp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjaxUtils.printString(response, jsonstr);
		return null;
	}

	@Override
	@RequestMapping(value="findPageAll_emp.do")
	public String findPageAll(HttpServletRequest request, HttpServletResponse response, Integer page, Integer rows) {
		Map<String,Object> map=new HashMap<String,Object>();
		PageBean pb=new PageBean();
		page=page==null||page<1?pb.getPage():page;
		rows=rows==null||rows<1?pb.getRows():rows;
		if(rows>10)rows=10;
		pb.setPage(page);
		pb.setRows(rows);
		//获取集合
		List<Emp> lsemp=bizService.getEmpBiz().findPageAll(pb);
		//获取总记录数
		int maxrows=bizService.getEmpBiz().findMaxRows();
		map.put("page", page);
		map.put("rows", lsemp);
		map.put("total", maxrows);
		PropertyFilter propertyFilter=AjaxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjaxUtils.printString(response, jsonstr);
		return null;
	}

	@Override
	@RequestMapping(value="doinit_emp.do")
	public String doinit(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Dep> lsdep=bizService.getDepBiz().findAll();
		List<Welfare> lswf=bizService.getWelfareBiz().findAll();
		map.put("lsdep", lsdep);
		map.put("lswf", lswf);
		PropertyFilter propertyFilter=AjaxUtils.filterProperts("birthday","pic");
		String jsonstr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AjaxUtils.printString(response, jsonstr);
		return null;
	}

}
