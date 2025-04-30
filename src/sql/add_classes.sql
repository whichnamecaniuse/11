-- 检查已有数据
SELECT * FROM academy;

-- 为各个学院添加班级
-- 计算机学院 (aid=1)
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '计算机科学与技术1班', 1);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '计算机科学与技术2班', 1);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '软件工程1班', 1);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '软件工程2班', 1);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '网络工程班', 1);

-- 数学学院 (aid=2)
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '数学与应用数学1班', 2);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '数学与应用数学2班', 2);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '信息与计算科学班', 2);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '统计学班', 2);

-- 外语学院 (aid=3)
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '英语1班', 3);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '英语2班', 3);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '日语班', 3);
INSERT INTO clazz VALUES(seq_clazz_cid.nextval, '法语班', 3);

-- 检查插入结果
SELECT c.*, a.aname FROM clazz c 
INNER JOIN academy a ON c.acacademyid = a.aid 
ORDER BY c.acacademyid, c.cid; 