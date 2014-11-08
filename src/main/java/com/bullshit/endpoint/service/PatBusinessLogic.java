package com.bullshit.endpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bullshit.endpoint.dao.CasesMapper;
import com.bullshit.endpoint.entity.Cases;

@Service("patLogic")
public class PatBusinessLogic {

	@Autowired
	CasesMapper casesMapper;

	public int savePatCase(Cases patCase) throws Exception {
		return casesMapper.insertSelective(patCase);
	}

}
