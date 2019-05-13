package com.momo.crud.bean;

import javax.validation.constraints.Pattern;

public class Loginer {
    private Integer userId;

    @Pattern(regexp="(^[a-zA-Z0-9_-]{5,10}$)|(^[\\u2E80-\\u9FFF]{2,5}$)",
    		message="用户名必须是5-10位的数字和字母的组合或是2-5位的中文")
    private String userName;

    private String userPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

	@Override
	public String toString() {
		return "Loginer [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + "]";
	}
    
}