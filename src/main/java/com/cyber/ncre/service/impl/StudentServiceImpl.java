package com.cyber.ncre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cyber.ncre.entity.CompuTestMsg;
import com.cyber.ncre.entity.Computest;
import com.cyber.ncre.entity.Eenrollmsg;
import com.cyber.ncre.entity.Student;
import com.cyber.ncre.entity.Testclas;
import com.cyber.ncre.entity.academy;
import com.cyber.ncre.entity.clazz;
import com.cyber.ncre.mapper.StudentMapper;
import com.cyber.ncre.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentMapper studentMapper;
	
	@Override
	public Student login(String sxid, String spwd) {
		return studentMapper.login(sxid,spwd);
	}

	@Override
	public String getemail(String sxid) {
		return studentMapper.getemail(sxid);
	}

	@Override
	public void resetPassword(String sxid, String randPassword) {
		Student student=new Student(sxid,randPassword);
		System.out.println(student);
		studentMapper.updatePassword(student);
	}

	@Override
	public List<academy> getAcademy() {
		return studentMapper.getAcademy();
	}

	@Override
	public List<clazz> getClasses(String academy) {
		return studentMapper.getClasses(academy);
	}

	@Override
	@Transactional
	public boolean register(Student student) {
		return studentMapper.register(student)>0;
	}

	@Override
	public List<Testclas> getTest() {
		return studentMapper.getTest();
	}

	@Override
	@Transactional
	public Boolean apply(Eenrollmsg apply, int sid, String ssex, String tenames) {
		
		Integer teid = studentMapper.find(tenames);
		// 如果找不到对应的考试类型，直接返回失败
		if (teid == null) {
			System.out.println("找不到考试类型：" + tenames);
			return false;
		}
		
		// 确保性别值符合数据库约束，只能是'男'或'女'
		String validSex = "男";
		if (ssex != null && ssex.equals("女")) {
			validSex = "女";
		}
		System.out.println("使用的性别值：" + validSex);
		
		Computest co = new Computest(); 
		Boolean flag;
		
		if(studentMapper.apply(apply)){
			int eid = studentMapper.selecteid(sid);
			co.setEid(eid);
			co.setTeid(teid);
			flag = studentMapper.apply2(co) && (studentMapper.modify(sid, validSex) > 0);
		} else {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public Eenrollmsg show(String sname) {
		int sid=studentMapper.select(sname);
		return studentMapper.show(sid);
	}

	@Override
	@Transactional
	public List<String> testes(String sname) {
		int sid=studentMapper.select(sname);
		int eid=studentMapper.selecteid(sid);
		int teid=studentMapper.findteid(eid);
		return studentMapper.selectename(teid);
	}

	@Override
	public String getstuation(String sname) {
		int sid=studentMapper.select(sname);
		int eid=studentMapper.selecteid(sid);
		return studentMapper.getstuation(eid);
	}

	@Override
	public CompuTestMsg ifbaomin(int sid) {
		// TODO Auto-generated method stub
		return studentMapper.ifbaomin(sid);
	}

	@Override
	public Eenrollmsg getmsg(int sid) {
		// 修复实现，从数据库获取学生的报名信息
		return studentMapper.show(sid);
	}

	@Override
	public java.util.Map<String, Object> getExamRoomInfo(int sid) {
		// 获取学生的考场信息
		return studentMapper.getExamRoomInfo(sid);
	}

}
