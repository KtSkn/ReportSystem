package jp.co.addon.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.addon.entity.Fundmst;

@Mapper
public interface FundmstMapper {

	List<Fundmst> findAll();
    
}
