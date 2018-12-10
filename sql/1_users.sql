create table users(
	id int(4) not null primary key auto_increment COMMENT '用户id',
	user_name varchar(20) not null COMMENT '用户名,为sim卡IMSI',
	imei varchar(20) not null COMMENT '手机IMEI',
	passwd char(60) not null COMMENT '密码',
	nick_name char(20) COMMENT '昵称',
	regist_date char(20) not null COMMENT '注册日期',
	photo varchar(80) not null default 'photo/photo_root.jpg' COMMENT '头像',
	sex int(4) not null default 1 COMMENT '0: 女; 1: 男',
	credit int(4) not null default 100 COMMENT '诚信度',
	age int(4) not null default 18 COMMENT '年龄',
	ban int(4) not null default 0 COMMENT '0: 未禁言 1: 禁言',
	show_community_loc int(4) not null default 0 COMMENT '0: 显示 1: 不显示',
	certificate int(4) not null default 0 COMMENT '0: 未实名认证; 1: 已实名认证', 
	number_valid_time varchar(20) not null default '2018-06-06' COMMENT '会员有效时间', 
	can_experience int(4) not null default 1 COMMENT '0: 不能体验会员; 1: 可以体验会员', 
	
	birthday varchar(20) not null COMMENT '生日',
	birthday_type int(4) not null default 0 COMMENT '生日日期类型：0: 农历; 1: 阳历',
	hobby varchar(60) COMMENT '兴趣爱好',
	height int(4) COMMENT '身高',
	weight int(4) COMMENT '体重',
	occupation varchar(10) COMMENT '职业',
	educational_level varchar(10) COMMENT '学历',
	graduate_school varchar(20) COMMENT '毕业学校',
	mypage_photo varchar(80) COMMENT '主页图片',
	
	autograph varchar(60) COMMENT '签名'
);

set global max_allowed_packet = 2*1024*1024*10;


#insert into users (user_name,passwd,regist_date) values("Lucy","123123","2018-06-20");
#insert into users (user_name,passwd,regist_date) values("Tony","123456","2018-06-24");
#insert into users (user_name,passwd,regist_date) values("Jack","000000","2017-02-04");
#insert into users (user_name,passwd,regist_date) values("root","111111","2011-11-11");

#insert into users (user_name, nick_name ,passwd,regist_date, imei, age, sex, birthday) values("Lucy1", 'Lucy1',"111111","2011-11-11", '010101010101', '18', '0', '2000-05-21');
#insert into users (user_name, nick_name ,passwd,regist_date, imei, age, sex, birthday) values("Lucy2", 'Lucy2',"111111","2013-11-11", '020202020202', '21', '0', '1997-02-16');
insert into users (user_name, nick_name ,passwd,regist_date, imei, age, sex, birthday) values("韩信1", '笑对人生',"111111","2013-11-11", '030303030303', '26', '1', '1992-05-17');





#insert into users (user_name,passwd,regist_date) values("陈平","222222","2012-07-15");
#insert into users (user_name,passwd,regist_date) values("萧何","333333","2014-09-23");
#insert into users (user_name,passwd,regist_date) values("刘邦","444444","2014-09-18");
#insert into users (user_name,passwd,regist_date) values("张良","55555","2016-03-02");
#insert into users (user_name,passwd,regist_date) values("韩信","666666","2016-03-24");

#alter table users add photo varchar(40) COMMENT '头像';
#alter table users add autograph varchar(40) COMMENT '签名';
#alter table users add sex int(4) not null default 1 COMMENT '0: 女; 1: 男';
#alter table users add certificate int(4) not null default 0 COMMENT '0: 未实名认证; 1: 已实名认证';
alter table users add credit int(4) not null default 100 COMMENT '诚信度';
alter table users add age int(4) not null default 18 COMMENT '年龄';
alter table users add ban int(4) not null default 0 COMMENT '0: 未禁言 1: 禁言';
alter table users add show_community_loc int(4) not null default 0 COMMENT '0: 显示 1: 不显示';

alter table users add birthday varchar(20) not null default '1992-07-25' COMMENT '生日';
alter table users add birthday_type int(4) not null default 0 COMMENT '生日日期类型：0: 农历; 1: 阳历';
alter table users add hobby varchar(60) COMMENT '兴趣爱好';
alter table users add height int(4) COMMENT '身高';
alter table users add weight int(4) COMMENT '体重';
alter table users add occupation varchar(10) COMMENT '职业';
alter table users add graduate_school varchar(20) COMMENT '毕业学校';
alter table users add educational_level varchar(10) COMMENT '学历';
alter table users add mypage_photo varchar(80) COMMENT '主页图片';

alter table users add imei varchar(20) not null COMMENT '手机IMEI';
alter table users add number_valid_time varchar(20) not null default '2018-06-06 12:12:12' COMMENT '会员有效时间';
alter table users add can_experience int(4) not null default 1 COMMENT '0: 不能体验会员; 1: 可以体验会员';

#update users set photo = concat('resources/photo/photo_', user_name, '.jpg');


select id, nick_name from users where user_name <> '韩信' and nick_name like '%韩信%' limit 0, 1;

insert into users() values();


