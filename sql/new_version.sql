create table new_version(
	id bigint not null primary key auto_increment COMMENT 'id',
	version_code char(10) not null COMMENT '版本号',
	note varchar(300) default '' COMMENT '版本更新说明'
);

insert into new_version(version_code) values ('1');

update new_version set version_code = 2, note = '1、添加社区认领功能\n2、更新消息标签';

alter table new_version add column note varchar(300) default '' COMMENT '版本更新说明';