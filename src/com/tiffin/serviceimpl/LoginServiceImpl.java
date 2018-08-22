package com.tiffin.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tiffin.common.StaticVariable;
import com.tiffin.dao.LoginDao;
import com.tiffin.exception.TiffinAppBuinessException;
//import com.isat.objects.Offer;
import com.tiffin.objects.User;
import com.tiffin.service.LoginService;

@Service(value = "LoginService")
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

	@Autowired
	LoginDao loginDao;

	@Override
	public User checkUser(User user) throws TiffinAppBuinessException {

		User loginUser = null;
		try {
			if (user != null && user.getUserName() != null && user.getPassword() != null) {
				loginUser = loginDao.validateUser(user);

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			if (e instanceof EmptyResultDataAccessException) {
				throw new TiffinAppBuinessException(StaticVariable.LOGIN_FAILURE, e.getMessage());
			} else {
				throw new TiffinAppBuinessException(StaticVariable.INTERNAL_FAILURE, e.getMessage());
			}
		}
		return loginUser;
	}

	public boolean insertUser(User user) throws TiffinAppBuinessException {
		boolean insertSucces = false;
		int registerUsercounter = 0;

		try {
			registerUsercounter = loginDao.register(user);
			System.out.println(registerUsercounter + "-----------registerUsercounter");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TiffinAppBuinessException(StaticVariable.INTERNAL_FAILURE, e.getMessage());
		}
		if (registerUsercounter == 1) {
			logger.info(registerUsercounter + "rows updated Successfully");
			System.out.println(registerUsercounter + "rows updated Successfully");
			insertSucces = true;
		} else{
			throw new TiffinAppBuinessException(StaticVariable.INSERT_FAILURE, null);
		}
		return insertSucces;
	}

}
