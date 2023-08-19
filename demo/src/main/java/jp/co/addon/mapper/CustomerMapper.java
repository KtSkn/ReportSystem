package jp.co.addon.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.addon.entity.Customer;

@Mapper
public interface CustomerMapper {

	List<Customer> findAll();
    
}
