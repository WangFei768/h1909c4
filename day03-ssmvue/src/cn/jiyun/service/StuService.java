package cn.jiyun.service;

import java.util.List;

import cn.jiyun.pojo.Major;
import cn.jiyun.pojo.QueryVo;
import cn.jiyun.pojo.Student;

public interface StuService {

	List<QueryVo> show();

	List<Major> findMajor();

	int addStu(Student stu);

	int updateStu(Student stu);

	int delStu(Integer[] ids);

}
