package com.tiffin.service;

import java.util.Set;

import com.tiffin.exception.TiffinAppBuinessException;
import com.tiffin.objects.User;

public interface LoginService {

	public User checkUser(User user) throws TiffinAppBuinessException;
	
	public boolean insertUser(User user) throws TiffinAppBuinessException;
	
	//public Set<String> checkOffering(String user) throws TiffinAppBuinessException;

}
