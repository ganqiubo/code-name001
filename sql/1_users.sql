create table users(
	id int(4) not null primary key auto_increment COMMENT '用户id',
	user_name char(20) not null unique COMMENT '用户名',
	passwd char(60) not null COMMENT '密码',
	nick_name char(20) COMMENT '昵称',
	regist_date char(20) not null COMMENT '注册日期'
);

#insert into users (user_name,passwd,regist_data) values("Tony","123456","2018-06-24");
#insert into users (user_name,passwd,regist_data) values("Jack","000000","2017-02-04");
#insert into users (user_name,passwd,regist_data) values("root","111111","2011-11-11");