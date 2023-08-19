package jp.co.addon.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jp.co.addon.entity.Customer;
import jp.co.addon.mapper.CustomerMapper;

@Repository
public class CustomerRepository {

    @Autowired
	private CustomerMapper customerMapper;

  public List<Customer> findAll() {
    return customerMapper.findAll();
  }

}
