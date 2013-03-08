package com.store.bean;

public class SoftwareBean {
	private String name;
	private String info;
	private String version;
	private String image_path;
	private String download_path;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public String getDownload_path() {
		return download_path;
	}
	public void setDownload_path(String download_path) {
		this.download_path = download_path;
	}

}
