package cn.jiyun.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.jiyun.pojo.Major;
import cn.jiyun.pojo.QueryVo;
import cn.jiyun.pojo.Student;

public interface StuMapper {

	List<QueryVo> show();

	List<Major> findMajor();

	int addStu(Student stu);

	int updateStu(Student stu);

	int delStu(@Param("ids")Integer[] ids);

	

}
