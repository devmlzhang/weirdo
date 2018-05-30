package com.weirdo.util;

public class WebsocketModel {
	private String type;  //类型
	private String operation; //操作  新增（add）、删除（del）、更新（upd）、变化（change）
	private Object data; //数据类型
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}