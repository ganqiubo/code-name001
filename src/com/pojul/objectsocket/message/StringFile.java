package com.pojul.objectsocket.message;

public class StringFile {
	
	public final static String pathBorderStart = "^#";
	public final static String pathBorderEnd = "#^";
	public final static String pathSendingBorderStart = "^0#";
	public final static String pathSendingBorderEnd = "#0^";
	public final static String regexStr = "\\^\\#.*?\\#\\^";
	public final static String regexServerStr1 = "\\{.*?\\^0\\#\\#0\\^.*?\\}";
	public final static String regexServerStr2 = "\\^0\\#\\#0\\^";
	
	public final static int STORAGE_TYPE_LOCAL = 0;
	
	protected String fileType;
	protected String fileName;
	protected String filePath;
	protected int storageType = 0;
	
	public StringFile(int storageType) {
		super();
		this.storageType = storageType;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		if(storageType == STORAGE_TYPE_LOCAL) {
			return filePath.replace(pathBorderStart, "").replace(pathBorderEnd, "");
		}else {
			return filePath;
		}
		
	}
	public void setFilePath(String filePath) {
		if(storageType == STORAGE_TYPE_LOCAL) {
			this.filePath = pathBorderStart + 
					filePath.replace(pathBorderStart, "").replace(pathBorderEnd, "")
					+ pathBorderEnd;
		}else {
			this.filePath = filePath;
		}
	}
	
}