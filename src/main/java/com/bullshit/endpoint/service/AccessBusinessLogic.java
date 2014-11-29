package com.bullshit.endpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bullshit.endpoint.constants.Constants;
import com.bullshit.endpoint.dao.AccountMapper;
import com.bullshit.endpoint.entity.Account;
import com.bullshit.endpoint.utils.StringUtil;

@Service("accessLogic")
public class AccessBusinessLogic {
	@Autowired
	private AccountMapper accountMapper;

	public Account getAccountInfo(String id) throws Exception {
		Account account = accountMapper.selectByPrimaryKey(id);
		account.setImageurl(StringUtil.urlPathEdit(account.getImageurl(), Constants.QINIU_PREFIX_URL));
		return account;
	}
	
	public int updateAccount(Account account) throws Exception {
		return accountMapper.updateByPrimaryKeySelective(account);
	}

}
