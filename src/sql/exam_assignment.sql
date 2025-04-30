-- 创建考场分配表
CREATE TABLE IF NOT EXISTS examAssignment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cid INT NOT NULL COMMENT '考试报名ID',
    roomName VARCHAR(50) NOT NULL COMMENT '考场名称',
    seatNumber VARCHAR(20) NOT NULL COMMENT '座位号',
    examDate VARCHAR(20) NOT NULL COMMENT '考试日期',
    examTime VARCHAR(20) NOT NULL COMMENT '考试时间',
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_cid (cid)
) COMMENT='考场分配信息表';

-- 添加索引
CREATE INDEX idx_cid ON examAssignment(cid);

-- 为notThrowLog表添加索引（如果不存在）
CREATE INDEX IF NOT EXISTS idx_cid ON notThrowLog(cid);

-- 更新computest表，确保有cstatus字段
ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' COMMENT '审核状态：待审核、审核通过、未通过'; 