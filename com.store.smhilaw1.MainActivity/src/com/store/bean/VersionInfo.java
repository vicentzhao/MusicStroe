package com.store.bean;

public class VersionInfo {
		private String name ;
		private  String packageName;
		private String version;
		private String filepath;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPackageName() {
			return packageName;
		}
		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getFilepath() {
			return filepath;
		}
		public void setFilepath(String filepath) {
			this.filepath = filepath;
		}
}
