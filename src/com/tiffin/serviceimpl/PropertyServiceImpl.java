package com.tiffin.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.tiffin.service.PropertyService;

@Service("PropertyService")
public class PropertyServiceImpl implements PropertyService{

	
	@Autowired
    private Environment environment;
	
	@Override
	public String readValues(String propertyKey) {
		return environment.getProperty(propertyKey);
		
	}

}
