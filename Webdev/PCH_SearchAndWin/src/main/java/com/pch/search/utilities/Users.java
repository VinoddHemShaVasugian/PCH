package com.pch.search.utilities;

import java.lang.reflect.Field;

import com.pch.search.utilities.User;

public class Users {	
	public User registeredUser;
	private User randomUser;
	private User randomUnsubscribedUser;
	public User getUser(String user) throws IllegalArgumentException, IllegalAccessException {
		for(Field field:this.getClass().getDeclaredFields()){
			if(field.getType().equals(User.class)&& field.getName().equals(user)){
				return (User)field.get(this);
			}else if(field.getType().equals(User.class)&& user.toLowerCase().startsWith("randomuser")){
				return randomUser;
			}else if(field.getType().equals(User.class)&& user.toLowerCase().startsWith("randomunsubscribeduser")){
				return randomUnsubscribedUser;
			}
		}
		return null;
	}	
}
