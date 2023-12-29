package com.core.tjoeun.index.store.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.core.tjoeun.index.store.dao.StoreDao;

@Service
@EnableTransactionManagement
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreDao storeDao;

	@Override
	@Transactional(readOnly= false)
	public List<Map> getBgmList(Map map) throws Exception {
		if(storeDao.getBgmList(map)==null) {
			throw new Exception();
		}
		return storeDao.getBgmList(map);
	}

	@Override
	@Transactional(readOnly = false)
	public List<Map> searchBgm(Map map) throws Exception {
		if(storeDao.getBgmList(map)==null) {
			throw new Exception();
		}		
		return storeDao.getBgmList(map);
	}
	
	@Override
	public List<Map> getProductList(Map map) throws Exception {
		if(storeDao.getBgmList(map)==null) {
			throw new Exception();
		}
		return storeDao.getProductList(map);
	}

}
