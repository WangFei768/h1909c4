package com.xiaoshu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Major;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.Student;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.StuService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("stu")
public class StuController extends LogController{
	static Logger logger = Logger.getLogger(StuController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	private StuService ss;
	
	
	//导入
		@RequestMapping("inStu")
		public void inStu(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			JSONObject result=new JSONObject();
			
			
			FileInputStream is = (FileInputStream) file.getInputStream();
			
			try {
				HSSFWorkbook workbook = new HSSFWorkbook(is);
				HSSFSheet sheet = workbook.getSheetAt(0);
				int lastRowNum = sheet.getLastRowNum();
				ArrayList<Student> list = new ArrayList<Student>();
				for (int i = 1; i <lastRowNum; i++) {
					HSSFRow row = sheet.getRow(i);
					Student student = new Student();
					student.setSname(row.getCell(1).getStringCellValue());
					student.setSex(row.getCell(2).getStringCellValue());
					String bir = row.getCell(3).getStringCellValue();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					student.setBirthday(simpleDateFormat.parse(bir));
					student.setHobby(row.getCell(4).getStringCellValue());
					
					ss.addStu(student);
				}
				
				result.put("success", true);
						
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("保存用户信息错误",e);
					result.put("success", true);
					result.put("errorMsg", "对不起，操作失败");
				}
				WriterUtil.write(response, result.toString());
		}
	
	
	
	@RequestMapping("stuIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		List<Major> mList = ss.findMajor();
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("mList", mList);
		return "stu";
	}
	
	
	@RequestMapping(value="stuList",method=RequestMethod.POST)
	public void userList(Student stu,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			/*User user = new User();
			String username = request.getParameter("username");
			String roleid = request.getParameter("roleid");
			String usertype = request.getParameter("usertype");
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			if (StringUtil.isNotEmpty(username)) {
				user.setUsername(username);
			}
			if (StringUtil.isNotEmpty(roleid) && !"0".equals(roleid)) {
				user.setRoleid(Integer.parseInt(roleid));
			}
			if (StringUtil.isNotEmpty(usertype)) {
				user.setUsertype(usertype.getBytes()[0]);
			}*/
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Student> stuList= ss.findStuPage(stu,pageNum,pageSize,ordername,order);
			
			/*request.setAttribute("username", username);
			request.setAttribute("roleid", roleid);
			request.setAttribute("usertype", usertype);*/
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",stuList.getTotal() );
			jsonObj.put("rows", stuList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("学生展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveStu")
	public void reserveUser(HttpServletRequest request,Student stu,MultipartFile file,String[] hobbys,HttpServletResponse response){
		Integer sId = stu.getSid();
		JSONObject result=new JSONObject();
		String hobby = "";
		hobby=StringUtils.join(hobbys, ",");
		stu.setHobby(hobby);
		/*for (String h : hobbys) {
			hobby+=h+",";
		}*/
		
		String filename = file.getOriginalFilename();
		
		try {
			if (sId != null) {   // userId不为空 说明是修改
				Student student = ss.findByName(stu.getSname());
				if((student != null && student.getSid().compareTo(sId)==0)||student==null){
					
					if(filename!=null && filename!=""){
					String newName = UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));	
					stu.setImg("/photo/"+newName);
					file.transferTo(new File("/photo/"+newName));
					
					}
					ss.updateStu(stu);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(ss.findByName(stu.getSname())==null){  // 没有重复可以添加
					
					if(filename!=null && filename!=""){
					String newName = UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf("."));	
					stu.setImg("/photo/"+newName);
					file.transferTo(new File("/photo/"+newName));
					
					}
					
					ss.addStu(stu);
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	@RequestMapping("deleteStu")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				ss.deleteStu(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
}
