package com.cyber.ncre.service.impl;

import java.util.List;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cyber.ncre.entity.CompuTestMsg;
import com.cyber.ncre.entity.academy;
import com.cyber.ncre.entity.academyAdmin;
import com.cyber.ncre.entity.clazz;
import com.cyber.ncre.entity.kaoroom;
import com.cyber.ncre.entity.news;
import com.cyber.ncre.entity.sysAdmin;
import com.cyber.ncre.mapper.sysAdminMapper;
import com.cyber.ncre.service.sysAdminService;
import org.apache.logging.log4j.LogManager;

@Service("sysAdminService")
public class sysAdminServiceImpl implements sysAdminService {
	
	@Autowired
	private sysAdminMapper sysAdminMapper;

	@Override
	public sysAdmin dologin(sysAdmin admin) {
		return sysAdminMapper.findsysAdmin(admin);
	}

	@Override
	public List<academyAdmin> doapply() {
		return sysAdminMapper.findapply();
	}

	@Override
	public boolean doagree(academyAdmin admin) {
		return sysAdminMapper.modifyAcstatusPass(admin)>0;
	}
	
	@Override
	public List<clazz> doacadMsg() {
		return sysAdminMapper.findacadMsg();
	}

	@Override
	public boolean dodisagree(academyAdmin admin) {
		return sysAdminMapper.modifyAcstatusNPass(admin)>0;
	}

	@Override
	public boolean dosetting(academy acad, clazz clazz) {
		// 修复学院/班级更新逻辑
		if (acad != null && acad.getAid() != null && acad.getAname() != null) {
			// 有学院参数时更新学院
			if (clazz != null && clazz.getCid() != null && clazz.getCname() != null) {
				// 学院和班级都有时，两者都更新
				return sysAdminMapper.setacadMsg(acad) > 0 && sysAdminMapper.setclazzMsg(clazz) > 0;
			} else {
				// 只有学院参数时，只更新学院
				return sysAdminMapper.setacadMsg(acad) > 0;
			}
		} else if (clazz != null && clazz.getCid() != null && clazz.getCname() != null) {
			// 只有班级参数时，只更新班级
			return sysAdminMapper.setclazzMsg(clazz) > 0;
		}
		// 两者都没有参数时，返回false
		return false;
	}

	@Override
	public boolean dosetting(clazz clazz) {
		return sysAdminMapper.delclazzMsg(clazz)>0;
	}

	@Override
	public academy findacad(academy acad) {
		return sysAdminMapper.selectAcadByName(acad);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW) //插入事务操作
	public void doAddacad(academy acad, clazz clazz) {
		//插入学院信息
		sysAdminMapper.addAcademy(acad);
		//插入班级信息
		sysAdminMapper.addClazz(clazz);
	}

	@Override
	public boolean doAddclazz(clazz clazz) {
		//插入已存在学院班级
		return sysAdminMapper.addExistClazz(clazz)>0;
	}

	@Override
	public boolean findclazz(clazz clazz) {
		return sysAdminMapper.selectClazz(clazz)==null;
	}

	@Override
	public List<news> donewsMsg() {
		return sysAdminMapper.getnewMsg();
	}

	@Override
	public boolean delnews(news news) {
		return sysAdminMapper.delnewMsg(news)>0;
	}

	@Override
	public boolean modifynews(news news) {
		return sysAdminMapper.updatenews(news)>0;
	}

	@Override
	public boolean addnews(news news) {
		return sysAdminMapper.addnews(news)>0;
	}

	@Override
	public List<kaoroom> dokaoMsg() {
		return sysAdminMapper.findkaoMsg();
	}

	@Override
	public boolean dosavekao(kaoroom kaoroom) {
		return sysAdminMapper.modifykaoRoom(kaoroom)>0;
	}

	@Override
	public boolean dodelkao(kaoroom kaoroom) {
		return sysAdminMapper.delkaoRoom(kaoroom)>0;
	}

	@Override
	public boolean doaddkao(kaoroom kaoroom) {
		return sysAdminMapper.addkaoRoom(kaoroom)>0;
	}

	@Override
	public List<CompuTestMsg> dobaoMsg() {
		return sysAdminMapper.findbaoMsg();
	}

	@Override
	public news getnewsMsgById(news news) {
		return sysAdminMapper.findnewsById(news);
	}

	@Override
	public boolean approveStudentApply(int cid) {
		// 审核通过学生报名申请
		boolean result = sysAdminMapper.approveStudentApply(cid) > 0;
		
		// 如果审核通过，自动分配考场和座位
		if (result) {
			try {
				autoAssignExamRoom(cid);
			} catch (Exception e) {
				LogManager.getLogger().error("自动分配考场失败: " + e.getMessage(), e);
				// 即使自动分配考场失败，审核通过状态仍然保留
			}
		}
		
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean rejectStudentApply(int cid, String reason) {
		// 拒绝学生报名申请并添加原因
		boolean updateResult = sysAdminMapper.rejectStudentApply(cid) > 0;
		boolean addReasonResult = sysAdminMapper.addRejectReason(cid, reason) > 0;
		
		// 移除考场分配
		try {
			sysAdminMapper.removeExamRoom(cid);
			LogManager.getLogger().info("已移除考场分配：cid=" + cid);
		} catch (Exception e) {
			LogManager.getLogger().warn("移除考场分配失败：cid=" + cid, e);
		}
		
		return updateResult && addReasonResult;
	}

	@Override
	public boolean assignExamRoom(int cid, String roomName, String seatNumber, String examDate, String examTime) {
		// 为学生分配考场
		return sysAdminMapper.assignExamRoom(cid, roomName, seatNumber, examDate, examTime) > 0;
	}

	@Override
	public boolean removeExamAssignment(int cid) {
		// 取消学生考场分配
		return sysAdminMapper.removeExamRoom(cid) > 0;
	}

	@Override
	public boolean resetStudentApplyStatus(int cid) {
		// 重置学生报名状态为"待审核"，同时移除考场分配
		boolean resetResult = sysAdminMapper.resetStudentApplyStatus(cid) > 0;
		// 移除考场分配
		sysAdminMapper.removeExamRoom(cid);
		return resetResult;
	}

	@Override
	public boolean updateStudentStatus(int cid, String status) {
		// 直接更新学生报名状态
		boolean updateResult = sysAdminMapper.updateStudentStatus(cid, status) > 0;
		
		// 如果状态不是"审核通过"，则移除考场分配
		if (!status.equals("审核通过")) {
			try {
				sysAdminMapper.removeExamRoom(cid);
				LogManager.getLogger().info("已移除考场分配：cid=" + cid);
			} catch (Exception e) {
				LogManager.getLogger().warn("移除考场分配失败：cid=" + cid, e);
			}
		}
		
		return updateResult;
	}

	@Override
	public int getAssignedSeatCount(String roomName, String examDate) {
		// 获取已分配座位数量
		return sysAdminMapper.countAssignedSeats(roomName, examDate);
	}

	@Override
	public List<String> getAssignedSeats(String roomName, String examDate) {
		// 获取已分配的座位号列表
		return sysAdminMapper.listAssignedSeats(roomName, examDate);
	}

	/**
	 * 自动分配考场和座位号
	 * @param cid 学生报名ID
	 * @return 分配是否成功
	 */
	private boolean autoAssignExamRoom(int cid) {
		// 获取所有可用考场
		List<kaoroom> rooms = dokaoMsg();
		if (rooms == null || rooms.isEmpty()) {
			LogManager.getLogger().warn("没有可用考场");
			return false;
		}
		
		// 设置默认考试日期为下个月的1号
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String examDate = sdf.format(calendar.getTime());
		
		// 设置默认考试时间为上午9点
		String examTime = "09:00:00";
		
		// 遍历考场，找到有空位的考场
		for (kaoroom room : rooms) {
			String roomName = room.getKrclass();
			int capacity = Integer.parseInt(room.getKrtotal());
			
			// 获取该考场已分配的座位数
			int assignedCount = getAssignedSeatCount(roomName, examDate);
			
			// 如果考场还有空位
			if (assignedCount < capacity) {
				// 获取已分配的座位号列表
				List<String> assignedSeats = getAssignedSeats(roomName, examDate);
				
				// 找到第一个可用的座位号
				int seatNumber = 1;
				while (assignedSeats.contains(String.valueOf(seatNumber)) && seatNumber <= capacity) {
					seatNumber++;
				}
				
				// 如果找到了可用座位
				if (seatNumber <= capacity) {
					// 分配考场
					boolean assigned = assignExamRoom(cid, roomName, String.valueOf(seatNumber), examDate, examTime);
					if (assigned) {
						LogManager.getLogger().info("自动分配考场成功: cid=" + cid + ", 考场=" + roomName + ", 座位=" + seatNumber);
						return true;
					}
				}
			}
		}
		
		LogManager.getLogger().warn("所有考场都已满，无法分配: cid=" + cid);
		return false;
	}

	@Override
	public List<clazz> findacadMsg() {
		return sysAdminMapper.findacadMsg();
	}
	
	@Override
	public List<clazz> findAcademiesByName(String academyName) {
		return sysAdminMapper.findAcademiesByName(academyName);
	}

	@Override
	public String getRejectReason(int cid) {
		return sysAdminMapper.getRejectReason(cid);
	}

}
