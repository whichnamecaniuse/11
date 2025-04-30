-- 检查notThrowLog表是否存在，不存在则创建
CREATE TABLE IF NOT EXISTS notThrowLog (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cid INT NOT NULL COMMENT '考试报名ID',
    nocontent TEXT COMMENT '不通过原因',
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

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
CREATE INDEX IF NOT EXISTS idx_cid_exam ON examAssignment(cid);

-- 为notThrowLog表添加索引
CREATE INDEX IF NOT EXISTS idx_cid_log ON notThrowLog(cid);

-- 修改computest表的cstatus字段和check约束
ALTER TABLE computest 
    DROP CHECK computest_chk_1,
    MODIFY COLUMN cstatus VARCHAR(20) DEFAULT '待审核' COMMENT '审核状态：待审核、审核通过、未通过、已删除',
    ADD CONSTRAINT computest_chk_1 CHECK (cstatus IN ('待审核', '审核通过', '未通过', '已删除'));

-- 创建系统公告表
CREATE TABLE IF NOT EXISTS announcements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容',
    imageUrl VARCHAR(255) DEFAULT NULL COMMENT '公告图片URL',
    status VARCHAR(20) NOT NULL DEFAULT 'draft' COMMENT '公告状态：draft(草稿)、published(已发布)、archived(已归档)',
    importance INT DEFAULT 0 COMMENT '重要程度：0(普通)、1(重要)、2(紧急)',
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    publishTime TIMESTAMP NULL COMMENT '发布时间',
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    createdBy VARCHAR(50) DEFAULT NULL COMMENT '创建者',
    CONSTRAINT announcements_chk_1 CHECK (status IN ('draft', 'published', 'archived')),
    CONSTRAINT announcements_chk_2 CHECK (importance IN (0, 1, 2))
) COMMENT='系统公告表';

-- 添加索引
CREATE INDEX IF NOT EXISTS idx_status ON announcements(status);
CREATE INDEX IF NOT EXISTS idx_publish_time ON announcements(publishTime);
CREATE INDEX IF NOT EXISTS idx_importance ON announcements(importance); 