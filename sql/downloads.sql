create table downloads(
	id bigint not null primary key auto_increment COMMENT 'id',
	apk_downloads bigint not null default 0 COMMENT '下载量'
);

insert into downloads values(0);

update downloads set apk_downloads = apk_downloads + 1 where id = 1;