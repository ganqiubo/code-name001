create table follow_person(
	id bigint not null primary key auto_increment COMMENT 'id',
	follow_user_name varchar(20) not null COMMENT '关注用户名',
	followed_user_name varchar(20) not null COMMENT '被关注用户名'
);

insert into follow_person(follow_user_name, followed_user_name) values ('Jack', 'root'), ('root', 'Jack')

select (select count(*) from follow_person where follow_user_name = 'root' and followed_user_name = 'Lucy') as follow_count, count(*) as like_count 
	from like_person where own_user_name = 'root' and liked_user_name = 'Lucy';
	
select *, 
	(select count(*) from friend where friend_uid = concat('root', '_', a.followed_user_name) or friend_uid = concat(a.followed_user_name, '_', 'root')) as is_friend
	from (select id as extend_id, followed_user_name from follow_person where follow_user_name = 'root' and id < (select (max(id) + 1) from follow_person) order by id desc limit 2) as a 
	inner join users as b on a.followed_user_name = b.user_name;
	
select count(*) as num from follow_person where followed_user_name = 'root';
	
select *, 
	(select count(*) from friend where friend_uid = concat('root', '_', a.follow_user_name) or friend_uid = concat(a.follow_user_name, '_', 'root')) as is_friend
	from (select id as extend_id, follow_user_name from follow_person where followed_user_name = 'root' and id < (select (max(id) + 1) from follow_person) order by id desc limit 2) as a 
	inner join users as b on a.follow_user_name = b.user_name;