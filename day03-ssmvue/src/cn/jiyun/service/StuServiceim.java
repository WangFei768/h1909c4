package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jiyun.mapper.StuMapper;
import cn.jiyun.pojo.Major;
import cn.jiyun.pojo.QueryVo;
import cn.jiyun.pojo.Student;
@Service
@Transactional
public class StuServiceim implements StuService {
@Autowired
private StuMapper p;
	public List<QueryVo> show() {
		// TODO Auto-generated method stub
		return p.show();
	}
	public List<Major> findMajor() {
		// TODO Auto-generated method stub
		List<Major> mlist = p.findMajor();
		return mlist;
	}
	public int addStu(Student stu) {
		// TODO Auto-generated method stub
		int i = p.addStu(stu);
		return i;
	}
	public int updateStu(Student stu) {
		// TODO Auto-generated method stub
		int i = p.updateStu(stu);
		return i;
	}
	public int delStu(Integer[] ids) {
		// TODO Auto-generated method stub
		int i = p.delStu(ids);
		return i;
	}

}
