package com.seezoon.eagle.session.demo.web.form;

import java.io.Serializable;

public class StudentForm implements Serializable{
	private String userName;
	private Integer age;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}