package com.mycrawler.das.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycrawler.common.dao.IPProxyMapper;
import com.mycrawler.common.domain.IPProxy;
import com.mycrawler.common.exception.MyRuntimeException;
import com.mycrawler.das.IIPProxyService;

@Service
public class IPProxyServiceImpl implements IIPProxyService{
	@Autowired
	private IPProxyMapper ipproxyMapper;
	@Override
	@Transactional
	public int insert(IPProxy ipproxy) {
		if(Objects.isNull(ipproxy))
			throw new MyRuntimeException();
		ipproxyMapper.insert(ipproxy);
		return 0;
	}

}
