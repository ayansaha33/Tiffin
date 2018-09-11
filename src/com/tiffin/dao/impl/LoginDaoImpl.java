package com.tiffin.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.tiffin.common.SQLConstants;
import com.tiffin.dao.LoginDao;
//import com.tiffin.objects.Offer;
//import com.tiffin.objects.Role;
import com.tiffin.objects.User;

@Repository(value = "LoginDao")
public class LoginDaoImpl implements LoginDao {

	private static final Logger logger = Logger.getLogger(LoginDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void register(User user) throws Exception {

		//int execusionStatus = 0;
		
		/*String sql="insert into TEMP_USER (USER_NAME,PASSWORD,EMAIL,PHONE,LOCATION)"
				+"values(?,?,?,?,?,?)";*/
		/*SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withCatalogName("prayojon_dev")
				.withProcedureName("CREATE_USER");*/
		
		sessionFactory.getCurrentSession().save(user);
		//execusionStatus=jdbcTemplate.update(sql,user.getUserName(),user.getPassword(),user.getEmail(),user.getPhone(),user.getLocation());

		/*Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("InUsername", user.getUserName().trim());
		inParamMap.put("InPassword", user.getPassword().trim());
		inParamMap.put("InEmail", user.getEmail().trim());
		inParamMap.put("InPhone", user.getPhone().trim());
		inParamMap.put("InLocation", user.getLocation().trim());
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> mapResult = simpleJdbcCall.execute(in);
		
		if (mapResult.get("InStatus") != null) {
			execusionStatus = Integer.parseInt(mapResult.get("InStatus").toString());
		}*/
		

	}

	@Override
	@SuppressWarnings("unchecked")
	public User validateUser(User user) throws Exception {
		/*User loginUser = jdbcTemplate.queryForObject(SQLConstants.IS_VALID_USER,
				new Object[] { user.getUserName().trim(), user.getPassword().trim() }, new UserMapper());
		System.out.println(loginUser.getEmail());*/
		Session session = sessionFactory.getCurrentSession();
		List<User> list=session.createQuery(SQLConstants.IS_VALID_USER)
				.setParameter(0, user.getUserName().trim())
				.setParameter(1, user.getPassword().trim()).list();
		return list.size() > 0 ?(User)list.get(0): null;
	}




class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();
		user.setUserName(rs.getString("USER_NAME"));
		user.setEmail(rs.getString("EMAIL"));
		user.setPhone(rs.getString("PHONE"));
		return user;
	}
}


}

