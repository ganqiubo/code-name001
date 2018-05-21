create table users(
	id int(4) not null primary key auto_increment COMMENT '用户id',
	user_name char(20) not null unique COMMENT '用户名',
	passwd char(60) not null COMMENT '密码',
	nick_name char(20) COMMENT '昵称',
	regist_date char(20) not null COMMENT '注册日期'
);

#insert into users (user_name,passwd,regist_date) values("Lucy","123123","2018-06-20");
#insert into users (user_name,passwd,regist_date) values("Tony","123456","2018-06-24");
#insert into users (user_name,passwd,regist_date) values("Jack","000000","2017-02-04");
#insert into users (user_name,passwd,regist_date) values("root","111111","2011-11-11");
#insert into users (user_name,passwd,regist_date) values("陈平","222222","2012-07-15");
#insert into users (user_name,passwd,regist_date) values("萧何","333333","2014-09-23");
#insert into users (user_name,passwd,regist_date) values("刘邦","444444","2014-09-18");
#insert into users (user_name,passwd,regist_date) values("张良","55555","2016-03-02");
#insert into users (user_name,passwd,regist_date) values("韩信","666666","2016-03-24");