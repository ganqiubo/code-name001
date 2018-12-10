create table community_room(
	id bigint not null primary key auto_increment COMMENT '社区id',
	community_uid varchar(40) not null unique COMMENT '社区uid',
	name char(20) not null default '' COMMENT '聊天室名称',
	create_date char(20) not null COMMENT '创建日期',
	community_type char(10) not null COMMENT '社区类型',
	community_subtype char(10) not null COMMENT '社区二级类型',
	country char(15) not null default '' COMMENT '所在国家',
	province char(15) not null default '' COMMENT '所在省份',
	city char(15) not null default '' COMMENT '所在城市',
	district char(15) not null default '' COMMENT '所在县/区',
	addr char(15) not null default '' COMMENT '所在详细地址',
	longitude double COMMENT '经度',
	latitude double COMMENT '纬度',
	altitude double COMMENT '海拔',
	photo varchar(120) not null default '' COMMENT '聊天室图片', 
	detail varchar(1200) not null default '' COMMENT '聊天室介绍',
	manager varchar(40) not null default '' COMMENT '聊天室管理员', 
	hsa_claimed int(4) not null default 0 comment '社区是否被认领',
	phone varchar(20) not null default '' COMMENT '联系电话'
);


ALTER table community_room ADD INDEX index_community_uid(community_uid);

alter table community_room add column photo varchar(120) not null default '' COMMENT '聊天室图片';
alter table community_room add column detail varchar(1200) not null default '' COMMENT '聊天室介绍';
alter table community_room add column manager varchar(40) not null default '' COMMENT '聊天室管理员';
alter table community_room add column phone varchar(20) not null default '' COMMENT '联系电话';
alter table community_room add column hsa_claimed int(4) not null default 0 comment '社区是否被认领';

select a.* from community_room as a inner join community_message as b on a.community_uid = b.community_uid and b.message_uid = '1534156408186_Lucy_2';