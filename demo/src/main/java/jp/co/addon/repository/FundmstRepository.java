package jp.co.addon.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.addon.entity.Fundmst;
import jp.co.addon.mapper.FundmstMapper;

@Repository
public class FundmstRepository {

    @Autowired
	private FundmstMapper fundmstMapper;

  public List<Fundmst> findAll() {
    return fundmstMapper.findAll();
  }
}