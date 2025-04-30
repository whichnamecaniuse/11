package com.cyber.ncre.service;

import java.util.List;

import com.cyber.ncre.entity.CompuTestMsg;
import com.cyber.ncre.entity.academy;
import com.cyber.ncre.entity.academyAdmin;
import com.cyber.ncre.entity.clazz;
import com.cyber.ncre.entity.kaoroom;
import com.cyber.ncre.entity.news;
import com.cyber.ncre.entity.sysAdmin;

public interface sysAdminService{

	sysAdmin dologin(sysAdmin admin);

	List<academyAdmin> doapply();

	boolean doagree(academyAdmin admin);

	boolean dodisagree(academyAdmin admin);

	boolean dosetting(academy acad, clazz clazz);

	List<clazz> doacadMsg();

	boolean dosetting(clazz clazz);

	academy findacad(academy acad);

	void doAddacad(academy acad, clazz clazz);

	boolean doAddclazz(clazz clazz);

	boolean findclazz(clazz clazz);

	List<news> donewsMsg();

	boolean delnews(news news);

	boolean modifynews(news news);

	boolean addnews(news news);

	List<kaoroom> dokaoMsg();

	boolean dosavekao(kaoroom kaoroom);

	boolean dodelkao(kaoroom kaoroom);

	boolean doaddkao(kaoroom kaoroom);

	List<CompuTestMsg> dobaoMsg();

	news getnewsMsgById(news news);
	
	// 审核通过学生报名申请
	boolean approveStudentApply(int cid);
	
	// 拒绝学生报名申请
	boolean rejectStudentApply(int cid, String reason);
	
	// 为学生分配考场
	boolean assignExamRoom(int cid, String roomName, String seatNumber, String examDate, String examTime);
	
	// 取消学生考场分配
	boolean removeExamAssignment(int cid);
	
	// 重置学生报名状态为"待审核"
	boolean resetStudentApplyStatus(int cid);
	
	// 直接更新学生报名状态
	boolean updateStudentStatus(int cid, String status);
	
	// 获取已分配座位数量
	int getAssignedSeatCount(String roomName, String examDate);
	
	// 获取已分配的座位号列表
	List<String> getAssignedSeats(String roomName, String examDate);

	public List<clazz> findacadMsg();
	
	// 按学院名称模糊查询
	public List<clazz> findAcademiesByName(String academyName);
	
	// 获取拒绝原因
	String getRejectReason(int cid);
}