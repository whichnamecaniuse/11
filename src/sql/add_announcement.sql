-- 检查announcements表是否有数据
SELECT COUNT(*) FROM announcements;

-- 清空announcements表数据（如果需要重新插入）
-- TRUNCATE TABLE announcements;

-- 插入系统公告样例数据
INSERT INTO announcements (title, content, status, importance, publishTime, createdBy)
VALUES 
('2024年上半年计算机等级考试报名开始', '各位同学：\n\n2024年上半年全国计算机等级考试报名现已开始，报名截止日期为2024年3月30日。请符合条件的同学尽快登录系统完成报名。\n\n详情请参考报名指南或联系学院管理员。', 'published', 2, NOW(), 'admin'),

('系统维护通知', '尊敬的用户：\n\n本系统将于2024年3月15日22:00-24:00进行系统维护，期间可能无法访问。请安排好您的使用时间，由此带来的不便敬请谅解。', 'published', 1, NOW(), 'admin'),

('考试报名填写须知', '请考生在填写报名信息时注意以下事项：\n\n1. 个人信息必须真实有效\n2. 上传的证件照必须清晰、近期拍摄\n3. 完成报名后请等待审核结果\n4. 审核通过后将分配考场，并可打印准考证\n\n如有疑问，请联系系统管理员。', 'published', 0, NOW(), 'admin'),

('考场规则公告', '考试当天请考生：\n\n1. 提前30分钟到达考场\n2. 携带有效身份证件和准考证\n3. 考场内禁止使用手机等电子设备\n4. 遵守考场秩序，服从监考人员安排\n\n祝各位考生考试顺利！', 'published', 0, NOW(), 'admin'),

('报名费用调整通知', '根据上级部门要求，自2024年4月1日起，计算机等级考试报名费用将有所调整。详细收费标准请查看官方文件或咨询学院管理员。', 'draft', 1, NULL, 'admin');

-- 检查插入结果
SELECT id, title, status, importance, DATE_FORMAT(createTime, '%Y-%m-%d %H:%i:%s') as createTime, 
       DATE_FORMAT(publishTime, '%Y-%m-%d %H:%i:%s') as publishTime 
FROM announcements 
ORDER BY importance DESC, publishTime DESC; 