create table like_person(
	id bigint not null primary key auto_increment COMMENT 'id',
	own_user_name varchar(20) not null COMMENT '喜欢用户名',
	liked_user_name varchar(20) not null COMMENT '被喜欢用户名'
);

insert into like_person(own_user_name, liked_user_name) values ('Lucy', 'root')

insert into like_person(own_user_name, liked_user_name) values ('韩信', 'root')

select c.*, 
	(select count(*) from friend where friend_uid = c.)
	from (select * from (select id as extend_id, liked_user_name from like_person where own_user_name = 'root' and id < (select (max(id) + 1) from like_person) order by id desc limit 2) as a 
	inner join users as b on a.liked_user_name = b.user_name) as c;
	
select *, 
	(select count(*) from friend where friend_uid = concat('root', '_', a.liked_user_name) or friend_uid = concat(a.liked_user_name, '_', 'root')) as is_friend
	from (select id as extend_id, liked_user_name from like_person where own_user_name = 'root' and id < (select (max(id) + 1) from like_person) order by id desc limit 2) as a 
	inner join users as b on a.liked_user_name = b.user_name;
	
select count(*) as num from like_person where liked_user_name = 'root';

select *, 
	(select count(*) from friend where friend_uid = concat('Lucy', '_', a.own_user_name) or friend_uid = concat(a.own_user_name, '_', 'Lucy')) as is_friend 
	from (select id as extend_id, own_user_name from like_person where liked_user_name = 'Lucy' 
	and id < 12 order by id desc limit 2) as a 
	inner join users as b on a.own_user_name = b.user_name;