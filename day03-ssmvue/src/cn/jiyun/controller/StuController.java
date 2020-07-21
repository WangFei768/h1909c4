package cn.jiyun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.jiyun.pojo.Major;
import cn.jiyun.pojo.QueryVo;
import cn.jiyun.pojo.Student;
import cn.jiyun.service.StuService;

@Controller
public class StuController {
@Autowired
private StuService s;
@RequestMapping("findAll")
public @ResponseBody PageInfo<QueryVo> show(Integer pageNum,Integer pageSize){
	PageHelper.startPage(pageNum, 3);
	List<QueryVo>list=s.show();
	PageInfo<QueryVo> pageInfo = new PageInfo<QueryVo>(list);
	return pageInfo;
	
}
@RequestMapping("Toshow")
public String Toshw(){
	return "show";
	
}
//跳转添加页面
@RequestMapping("toAdd")
public String toAdd(){
	return "add";
	
}
//查询班级列表
@ResponseBody
@RequestMapping("findMajor")
public List<Major> findMajor(){
	
	List<Major> mlist = s.findMajor();
	return mlist;
}
//添加
@ResponseBody
@RequestMapping("addStu")
public int addStu(@RequestBody Student stu){
	int i = s.addStu(stu);
	
	return i;
}
//修改
@ResponseBody
@RequestMapping("updateStu")
public int updateStu(@RequestBody Student stu){
	int i = s.updateStu(stu);
	
	return i;
}

//删除
@ResponseBody
@RequestMapping("delStu")
public int delStu(@RequestBody Integer[] ids){
	int i = s.delStu(ids);
	return i;
}

}
