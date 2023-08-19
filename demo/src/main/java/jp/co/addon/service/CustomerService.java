package jp.co.addon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.addon.dto.MyDto;
import jp.co.addon.entity.Chohyo;
import jp.co.addon.entity.Customer;
import jp.co.addon.form.MyForm;

@Service
public interface CustomerService {

	List<Customer> findAll();
	
	List<Chohyo> findAllChohyo();

	MyDto init(MyDto myDto);

	void getReportOutput(HttpServletResponse htResponse);

	MyForm init(MyForm myForm);

	
}
