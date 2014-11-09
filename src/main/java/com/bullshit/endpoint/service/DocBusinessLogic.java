package com.bullshit.endpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bullshit.endpoint.dao.DocSurgeryPlanMapper;
import com.bullshit.endpoint.entity.DocSurgeryPlan;

@Service("docLogic")
public class DocBusinessLogic{
	
	@Autowired
	private DocSurgeryPlanMapper docSurgeryPlanMapper;

	public int saveSurgeryPlan(DocSurgeryPlan docSurgeryPlan) throws Exception {
		return docSurgeryPlanMapper.insertSelective(docSurgeryPlan);
	}
}
