create table makeQRCode(
	commun_room_uid char(40) not null COMMENT '社区uid',
	milli bigint not null COMMENT '时间',
	create_milli bigint not null COMMENT '创建时间'
);

insert into makeQRCode(commun_room_uid, milli, create_milli) values('洪山区_泷悦华府', 1540817751012, 1540817751012);

update makeQRCode set milli = 1540817751013, create_milli = 1540817751014 where commun_room_uid = '洪山区_泷悦华府';
select count(*) as num from makeQRCode where commun_room_uid = '洪山区_泷悦华府' and milli = 1540817751013 and create_milli > 1540817750014;
select * from community_room where community_uid = '洪山区_泷悦华府';
