-- 初始化院系管理员测试数据

-- 检查academyadmin表是否存在，不存在则创建
CREATE TABLE IF NOT EXISTS academyadmin (
    acid INT AUTO_INCREMENT PRIMARY KEY,
    acloginname VARCHAR(50) NOT NULL COMMENT '登录名/姓名',
    acemail VARCHAR(100) NOT NULL COMMENT '邮箱',
    acloginpwd VARCHAR(50) NOT NULL COMMENT '密码',
    acsex VARCHAR(10) NOT NULL COMMENT '性别',
    acpicture VARCHAR(255) DEFAULT NULL COMMENT '照片',
    acwork VARCHAR(50) DEFAULT NULL COMMENT '职位',
    acacademyid INT NOT NULL COMMENT '所属学院ID',
    acphone VARCHAR(20) DEFAULT NULL COMMENT '电话',
    actime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    acstatus VARCHAR(20) DEFAULT '待审核' COMMENT '状态：待审核、审核通过、未通过'
);

-- 先确保几个学院存在
INSERT IGNORE INTO academy (aname) VALUES 
('计算机学院'), 
('数学学院'), 
('外国语学院'), 
('物理学院');

-- 删除已有的测试数据（如果存在）
DELETE FROM academyadmin WHERE acloginname IN ('张老师', '李老师', '王老师', '刘老师') AND acstatus = '待审核';

-- 添加测试数据 - 计算机学院管理员
INSERT INTO academyadmin (acloginname, acemail, acloginpwd, acsex, acpicture, acwork, acacademyid, acphone, actime, acstatus)
SELECT '张老师', 'zhang@example.com', '123456', '男', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 
       '讲师', a.aid, '13800138001', NOW(), '待审核'
FROM academy a
WHERE a.aname = '计算机学院'
LIMIT 1;

-- 添加测试数据 - 数学学院管理员
INSERT INTO academyadmin (acloginname, acemail, acloginpwd, acsex, acpicture, acwork, acacademyid, acphone, actime, acstatus)
SELECT '李老师', 'li@example.com', '123456', '女', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 
       '副教授', a.aid, '13800138002', NOW(), '待审核'
FROM academy a
WHERE a.aname = '数学学院'
LIMIT 1;

-- 添加测试数据 - 外国语学院管理员
INSERT INTO academyadmin (acloginname, acemail, acloginpwd, acsex, acpicture, acwork, acacademyid, acphone, actime, acstatus)
SELECT '王老师', 'wang@example.com', '123456', '男', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 
       '教授', a.aid, '13800138003', NOW(), '待审核'
FROM academy a
WHERE a.aname = '外国语学院'
LIMIT 1;

-- 添加测试数据 - 物理学院管理员
INSERT INTO academyadmin (acloginname, acemail, acloginpwd, acsex, acpicture, acwork, acacademyid, acphone, actime, acstatus)
SELECT '刘老师', 'liu@example.com', '123456', '女', 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png', 
       '讲师', a.aid, '13800138004', NOW(), '待审核'
FROM academy a
WHERE a.aname = '物理学院' 
LIMIT 1;

-- 查看添加的测试数据
SELECT aa.acid, aa.acloginname, aa.acsex, a.aname as academy_name, aa.acwork, aa.acphone, aa.actime, aa.acstatus
FROM academyadmin aa
JOIN academy a ON aa.acacademyid = a.aid
WHERE aa.acstatus = '待审核'; 