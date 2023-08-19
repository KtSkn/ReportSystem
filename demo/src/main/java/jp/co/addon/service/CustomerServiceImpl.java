package jp.co.addon.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.addon.dto.MyDto;
import jp.co.addon.entity.Chohyo;
import jp.co.addon.entity.Customer;
import jp.co.addon.entity.Fundmst;
import jp.co.addon.form.MyForm;
import jp.co.addon.mapper.ChohyoMapper;
import jp.co.addon.mapper.CustomerMapper;
import jp.co.addon.mapper.FundmstMapper;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private FundmstMapper fundmstMapper;

	@Autowired
	private ChohyoMapper chohyoMapper;

	@Autowired
	private ApplicationContext context;

	@Override
	@Transactional
	public List<Customer> findAll() {
		return customerMapper.findAll();
	}

	@Override
	@Transactional
	public List<Chohyo> findAllChohyo() {
		return chohyoMapper.findAllChohyo();
	}

	@Override
	public MyDto init(MyDto myDto) {
		myDto.setText("初期値だよ");
		myDto.setChecked(true);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(date);
		myDto.setWariate(sdf.format(date));
		return myDto;
	}

	@Override
	public MyForm init(MyForm myForm) {
		myForm.setText("初期値だよ");
		myForm.setChecked(true);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(date);
		myForm.setWariate(sdf.format(date));
		return myForm;
	}

	@Override
	public void getReportOutput(HttpServletResponse response) {


			List<Fundmst> fList = fundmstMapper.findAll();
			System.out.println(fList);
			fList.add(new Fundmst());
			fList.add(new Fundmst());
			fList.add(new Fundmst());
			
			try {
		      Resource resource = context.getResource("classpath:templates/tpc3.jrxml");
		      InputStream in = resource.getInputStream();
		      JasperReport report = JasperCompileManager.compileReport(in);

//		      String jasperFilePath =  resource.getFile().getPath();
		      
		      //パラメーター、データソースの設定
		      Map<String, Object> params = new HashMap<>();
		      params.put("fund1","abcd");
		      JREmptyDataSource dataSource = new JREmptyDataSource();
		      JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(fList);
		      //PDFを作成し、レスポンスボディに設定
		      JasperPrint print = JasperFillManager.fillReport(report, params, jrBeanCollectionDataSource);
		      response.setContentType(MediaType.APPLICATION_PDF_VALUE);
				JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());
				JasperExportManager.exportReportToPdf(print);
			  
//			  JasperExportManager.exportReportToPdfFile(print, "jasper.pdf");
				
//			  response.getOutputStream().flush(); 
//			  response.getOutputStream().close(); 
			} catch (JRException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

	}

}
