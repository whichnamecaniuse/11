-- 创建考场分配表
CREATE TABLE IF NOT EXISTS examAssignment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cid INT NOT NULL,
    roomName VARCHAR(50) NOT NULL,
    seatNumber VARCHAR(20) NOT NULL,
    examDate VARCHAR(20) NOT NULL,
    examTime VARCHAR(20) NOT NULL,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_cid (cid)
);

-- 创建不通过原因记录表（如果不存在）
CREATE TABLE IF NOT EXISTS notThrowLog (
    noid INT AUTO_INCREMENT PRIMARY KEY,
    cid INT NOT NULL,
    nocontent VARCHAR(400),
    INDEX idx_cid (cid)
);

-- 创建序列（如果需要）
CREATE SEQUENCE IF NOT EXISTS seq_noid START WITH 1001;

-- 为了防止examAssignment表没有正确创建，确保computest表中cstatus字段的定义正确
ALTER TABLE computest MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' CHECK (cstatus IN ('待审核','审核通过','未通过','已删除')); 