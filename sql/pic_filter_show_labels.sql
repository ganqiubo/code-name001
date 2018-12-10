create table pic_filter_show_labels(
	id bigint not null primary key auto_increment COMMENT 'id',
	label varchar(10) not null COMMENT '标签'
);

insert into pic_filter_show_labels(label) values("精选"),("风景"), ("生活"), ("建筑"), ("自拍"), ("摄影"), ("手机壁纸"), ("文艺"), ("清新"), 
	("美女"), ("萝莉"), ("迷人"), ("宠物"), ("写真"), ("沙滩"), ("大海"), ("古典"), ("唯美"), ("内涵"), ("可爱"), ("校花"), ("家居"), ("旅游");