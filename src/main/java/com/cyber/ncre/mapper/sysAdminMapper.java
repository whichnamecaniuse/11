package com.cyber.ncre.mapper;

import java.util.List;

import com.cyber.ncre.entity.CompuTestMsg;
import com.cyber.ncre.entity.academy;
import com.cyber.ncre.entity.academyAdmin;
import com.cyber.ncre.entity.clazz;
import com.cyber.ncre.entity.kaoroom;
import com.cyber.ncre.entity.news;
import com.cyber.ncre.entity.sysAdmin;
import org.apache.ibatis.annotations.Param;

public interface sysAdminMapper {

	sysAdmin findsysAdmin(sysAdmin admin);

	List<academyAdmin> findapply();

	int modifyAcstatusPass(academyAdmin admin);

	int modifyAcstatusNPass(academyAdmin admin);

	List<clazz> findacadMsg();
	
	int setacadMsg(academy acad);

	int setclazzMsg(clazz clazz);

	int delclazzMsg(clazz clazz);

	academy selectAcadByName(academy acad);

	int addAcademy(academy acad);

	int addClazz(clazz clazz);

	int addExistClazz(clazz clazz);

	clazz selectClazz(clazz clazz);

	List<news> getnewMsg();

	int delnewMsg(news news);

	int updatenews(news news);

	int addnews(news news);

	List<kaoroom> findkaoMsg();

	int modifykaoRoom(kaoroom kaoroom);

	int delkaoRoom(kaoroom kaoroom);

	int addkaoRoom(kaoroom kaoroom);

	List<CompuTestMsg> findbaoMsg();

	news findnewsById(news news);

	// 审核通过学生报名申请
	int approveStudentApply(@Param("cid") int cid);
	
	// 拒绝学生报名申请
	int rejectStudentApply(@Param("cid") int cid);
	
	// 添加拒绝原因
	int addRejectReason(@Param("cid") int cid, @Param("reason") String reason);
	
	// 为学生分配考场
	int assignExamRoom(@Param("cid") int cid, 
                       @Param("roomName") String roomName, 
                       @Param("seatNumber") String seatNumber, 
                       @Param("examDate") String examDate, 
                       @Param("examTime") String examTime);
	
	// 重置学生报名状态为"待审核"
	int resetStudentApplyStatus(@Param("cid") int cid);
	
	// 直接更新学生报名状态
	int updateStudentStatus(@Param("cid") int cid, 
	                         @Param("status") String status);
	
	// 获取已分配座位数量
	int countAssignedSeats(@Param("roomName") String roomName, 
	                       @Param("examDate") String examDate);
	
	// 获取已分配的座位号列表
	List<String> listAssignedSeats(@Param("roomName") String roomName, 
	                              @Param("examDate") String examDate);

	// 取消学生考场分配
	int removeExamRoom(@Param("cid") int cid);

	// 按学院名称模糊查询学院和班级
	List<clazz> findAcademiesByName(@Param("academyName") String academyName);
	
	// 获取拒绝原因
	String getRejectReason(@Param("cid") int cid);

}
