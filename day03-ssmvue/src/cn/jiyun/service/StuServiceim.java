package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jiyun.mapper.StuMapper;
import cn.jiyun.pojo.QueryVo;
@Service
@Transactional
public class StuServiceim implements StuService {
@Autowired
private StuMapper p;
	public List<QueryVo> show() {
		// TODO Auto-generated method stub
		return p.show();
	}

}
