package com.tiffin.dao;

import java.util.List;
//import com.tiffin.objects.Offer;
//import com.tiffin.objects.Role;
import com.tiffin.objects.User;

public interface LoginDao {

	int register(User user) throws Exception ;

	User validateUser(User user) throws Exception;
	
	//List<Offer> getOfferingByUser(String user) throws Exception;
	
	//Role getRoleByUser(User user) throws Exception;
	


}
