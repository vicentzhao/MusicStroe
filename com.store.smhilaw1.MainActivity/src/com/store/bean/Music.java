package com.store.bean;

import java.util.HashMap;

public class Music {
	
	private String name;
	private String pubName;
	private String info;
	private String version;
	private String image_path;
	private String download_path;
	private String id;
	private PostMent postMent;
	public PostMent getPostMent() {
		return postMent;
	}
	public void setPostMent(PostMent postMent) {
		this.postMent = postMent;
	}
	public String getPubName() {
		return pubName;
	}
	public void setPubName(String pubName) {
		this.pubName = pubName;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
   
}
