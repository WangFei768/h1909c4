package cn.jiyun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiyun.pojo.QueryVo;
import cn.jiyun.service.StuService;

@Controller
public class StuController {
@Autowired
private StuService s;
@RequestMapping("findAll")
public @ResponseBody List<QueryVo> show(){
	List<QueryVo>list=s.show();
	return list;
	
}
@RequestMapping("Toshow")
public String Toshw(){
	return "show";
	
}
}
