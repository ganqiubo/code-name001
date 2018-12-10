package com.pojul.fastIM.entity;

import java.sql.ResultSet;

import com.pojul.objectsocket.message.StringFile;

public class Pic extends BaseEntity{

	private Long id;
	private Long uploadPicId;
	private StringFile uploadPicUrl;
	private int width;
	private int height;
	private int isDelete= 1;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUploadPicId() {
		return uploadPicId;
	}
	public void setUploadPicId(Long uploadPicId) {
		this.uploadPicId = uploadPicId;
	}
	public StringFile getUploadPicUrl() {
		return uploadPicUrl;
	}
	public void setUploadPicUrl(StringFile uploadPicUrl) {
		this.uploadPicUrl = uploadPicUrl;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	@Override
	public void setBySql(ResultSet rs) {
		// TODO Auto-generated method stub
		super.setBySql(rs);
		if(rs == null) {
			return;
		}
		id = getLong(rs, "id");
		uploadPicId = getLong(rs, "upload_pic_id");
		uploadPicUrl = getStringFile(rs, "upload_pic_url");
		width = getInt(rs, "width");
		height = getInt(rs, "height");
		isDelete = getInt(rs, "is_delete");
	}
}
