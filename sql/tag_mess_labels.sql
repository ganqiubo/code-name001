create table tag_mess_labels(
	id int not null primary key auto_increment COMMENT 'id',
	label varchar(20) not null COMMENT '标签'
);

delete from tag_mess_labels;
insert into tag_mess_labels(label) values ('运动'), ('求助'), ('旅游'), ('社团'), ('驴友'), ('娱乐'), ('动态'), ('找室友'), ('游戏'), ('房屋出租'),
	('租房'), ('招聘'), ('好无聊'), ('俱乐部');
insert into tag_mess_labels(label) values ('公告通知');


select GROUP_CONCAT(label SEPARATOR ',') as labels from tag_mess_labels;