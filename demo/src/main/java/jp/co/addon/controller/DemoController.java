package jp.co.addon.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletResponse;
import jp.co.addon.entity.Chohyo;
import jp.co.addon.entity.ChohyoDto;
import jp.co.addon.entity.Customer;
import jp.co.addon.form.MyForm;
import jp.co.addon.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DemoController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final CustomerService cuService;

	@GetMapping("/index")
	public String index(Model model) {
		List<Customer> customerList = cuService.findAll();
		System.out.println(customerList);
		model.addAttribute("customer", customerList);

		MyForm myForm = new MyForm();
		
		model.addAttribute("myForm", cuService.init(myForm));

		decimalTest();

		return "index";
	}

	@PostMapping("/execute")
	public String execute(Model model, MyForm myForm, BindingResult result,HttpServletResponse response) {

		//エラーの場合は画面をを返す

		if(result.hasErrors()) {
			
			String field = "";
			String message = "";
			for (FieldError error : result.getFieldErrors()) {
				  field = error.getField();
				  message = error.getDefaultMessage();
				  System.out.println(field);
				  System.out.println(message);
			}
			System.out.println(myForm);
//			MyDto myDto = cuService.init(new MyDto());
//			myDto.setMessage(message);
//				model.addAttribute("myForm", myForm);

			return "index";
		}


		executeReport(response);
		//    	return "redirect:index";
		return null;
	}

//	@GetMapping("jasper")
	public void executeReport(HttpServletResponse response) {
		cuService.getReportOutput(response);

	}


	private void decimalTest() {
		
		BigDecimal bd = BigDecimal.valueOf(10.7777111);
		System.out.println(bd);

		System.out.println("----小数部------");
		//小数部
        BigDecimal fractionalPart = bd.remainder( BigDecimal.ONE );//1で割ったあまり
        System.out.println(fractionalPart);
		
		
        System.out.println("----整数部(小数以下切り捨て)------");
		// 小数第一位を切り捨て
		BigDecimal integer = bd.setScale(0, RoundingMode.DOWN);
		System.out.println(integer);
		
		
		//文字列に変換
		System.out.println("----文字列へ------");
		System.out.println(integer.toString());
		System.out.println(integer.toPlainString());
		
		//値比較
		BigDecimal bd1 = BigDecimal.valueOf(1);
		
		if(bd1.compareTo(BigDecimal.ZERO) == 0) {
			
			System.out.println("bd1 と 1 は同じです");
			
		}
		
		BigDecimal bd2 = new BigDecimal(1.23455);
		BigDecimal bd3 = new BigDecimal(2.56789);
		BigDecimal bd4 = new BigDecimal(0.486789);
		BigDecimal result;
		
		result = bd2.divide(bd3, 10, RoundingMode.HALF_UP);
		result = bd4.setScale(2, RoundingMode.HALF_UP);
		
		System.out.println(result);
		
		
		
		
		
		
        
	}
	
	
	
	
	private void streamTest() {

		List<Chohyo> chList = cuService.findAllChohyo();

		List<ChohyoDto> chDtoList = new ArrayList<>();
		
		
		for (int i = 0; i < chList.size(); i++) {
			
			ChohyoDto chDto = new ChohyoDto();
			chDto.setHeader1(chList.get(i).getHeader1());
			chDto.setHeader2(chList.get(i).getHeader2());
			chDto.setHeader3(chList.get(i).getHeader3());
			chDto.setMeisai1(chList.get(i).getMeisai1().toString());
			chDto.setMeisai2(chList.get(i).getMeisai2());
			chDto.setMeisai3(chList.get(i).getMeisai3());
			chDto.setGoukei1(BigDecimal.ZERO);
			chDto.setGoukei2(BigDecimal.ZERO);
			chDto.setGoukei3(BigDecimal.ZERO);
			chDtoList.add(chDto);
		}


		//複合キーの設定
		Function<Chohyo, String> compositeKey = chh -> {
			StringBuffer sb = new StringBuffer();
			sb.append(chh.getHeader1()).append("-").append(chh.getHeader2()).append("-").append(chh.getHeader3());
			return sb.toString();
		};

		//ヘッダーのグルーピング
		TreeMap<String, List<Chohyo>> grpByKey = chList.stream().collect(Collectors.groupingBy(compositeKey,
				TreeMap<String, List<Chohyo>>::new, Collectors.mapping(u -> u, Collectors.toList())));

		
		
		TreeMap<Integer, Integer> pageMeisaiMap = new TreeMap<>();

		int page = 0;
		int meisai = 0;

		//ページ数と明細数のマップを作成
		for (String key : grpByKey.keySet()) {
			page++;
			meisai = grpByKey.get(key).size();
			//改ページ条件
			if(meisai % 12 == 0) {
				page += meisai/12;
			}
			pageMeisaiMap .put(page, meisai);
			
			System.out.println(page +"ページ目");
			System.out.println(key +"：キー");
			System.out.println(grpByKey.get(key));
			System.out.println(pageMeisaiMap);
			
			
		}	
		//DTOにページを設定
		int indexStart = 0;
		int indexEnd = 0;
		for (Integer key : pageMeisaiMap.keySet()) {
			
			indexEnd += pageMeisaiMap.get(key);
			for (int i = indexStart; i < indexEnd; i++) {//明細分ループ
				
				chDtoList.get(i).setPage(key);
			}
			indexStart +=  pageMeisaiMap.get(key);
		
		}
		
		
		//ページ毎の合計
//		Map<Integer, BigDecimal> grpByTypeSum = chListDto.stream().collect(
//                Collectors.groupingBy(ChohyoDto::getPage, 
//                    Collectors.reducing(BigDecimal.ZERO, ChohyoDto::getMeisai1, BigDecimal::add)));

		//ページとオブジェクトのマップを作成
		Map<Integer, List<ChohyoDto>> grpByPage = chDtoList.stream().collect(
              Collectors.groupingBy(ChohyoDto::getPage));

		//ページ毎の合計
		Map<Integer, BigDecimal> pageSum = new HashMap<>();

		BigDecimal sum = new BigDecimal(0);

		//ページ毎にマップ展開
		for(Integer key : grpByPage.keySet()) {
			System.out.println(key);
			for(ChohyoDto c : grpByPage.get(key)) {
				BigDecimal bd = new BigDecimal(c.getMeisai1());
				BigDecimal work = sum;
				sum = work.add(bd);
			}
			
			pageSum.put(key, sum);
			sum = BigDecimal.ZERO;
		}
		
		
		System.out.println(pageSum);
		
		//合計値を設定
		for (int i = 0; i < chDtoList.size(); i++) {
			chDtoList.get(i).setGoukei1(pageSum.get(chDtoList.get(i).getPage()));
			
		}	
		System.out.println(chDtoList);
		
		
	}
}
			
//				page++;
//				
//				System.out.println("i : " + i );
//				System.out.println("meisai : " + meisai );
//				if(i == meisai) { //ループ+1が明細9の数に達したらループをぬける
//					break;
//				}
//				if(chListDto.get(i).getPage() == 0) {
//					chListDto.get(i).setPage(page);
//					
//				}
			
			
			
			//グループの合計
//			sumMap.put(key,
//					grpByKey.get(key).stream().map(Chohyo::getMeisai1).reduce(BigDecimal.ZERO, BigDecimal::add));

		//それぞれのグループの合計値を出す
//		System.out.println("grpByKey :" + grpByKey);
//		System.out.println("sumMap :" + sumMap);
//		//キーはなんだろ
//		for (int i = 0; i < chList.size(); i++) {
//			Chohyo ch = new Chohyo();
//			ch.setHeader1(chList.get(i).getHeader1());
//			ch.setHeader2(chList.get(i).getHeader2());
//			ch.setHeader3(chList.get(i).getHeader3());
//
//			StringBuffer sb = new StringBuffer();
//			sb.append(ch.getHeader1()).append("-").append(ch.getHeader2()).append("-").append(ch.getHeader3());
//			ch.setGoukei1(sumMap.get(sb.toString()));
////			chListEdit.add(ch);
//		}
//		
//		System.out.println("chListDto :" + chListDto);
		//listに入れて終わり

//	}

	//    private void streamTest() {
	//    	
	//    	List<Chohyo> chList =  cuService.findAllChohyo();
	//    	
	//    	System.out.println("---stream test------");
	//    	System.out.println(chList.size());
	//    	
	//    	BigDecimal bd = new BigDecimal(10);
	//    	BigDecimal bd2 = new BigDecimal(2);
	//    	System.out.println(bd);
	//    	System.out.println(bd2);
	//    	System.out.println(bd.add(bd2).add(bd2));

	//    	BigDecimal total = chList.stream().map(Chohyo::getMeisai1).reduce(BigDecimal.ZERO, BigDecimal::add);
	//    	System.out.println("-------total(総合計)-----------");
	//    	System.out.println(total);
	//    	System.out.println("-----------------");
	//    	
	//    	String str = "";
	//    	String beforestr = "";
	//    	BigDecimal meisai1sum = new BigDecimal(0);
	//    	for(int i=0;i<chList.size();i++) {
	//    		str = chList.get(i).getHeader1() 
	//    				+ chList.get(i).getHeader2()
	//    				+ chList.get(i).getHeader3();
	//    		
	//    		System.out.println("------------"+ (i+1) + "回目ループ---------start------");
	//    		System.out.println("str" + str);
	//    		System.out.println("beforestr" + beforestr);
	//    		
	//    		if(str.equals(beforestr) || i == 0) {
	//    			meisai1sum = chList.get(i).getMeisai1().add(meisai1sum);
	//    			System.out.println("明細1 合計：" + meisai1sum);
	//    		}
	//    		beforestr = str;
	//    		System.out.println("beforestr" + beforestr);
	//    		System.out.println("----------ループend--------");
	//    	}
	//    	System.out.println(meisai1sum);
	//    	
	//
	//    }

//}


//private void streamTest() {
//
//	List<Chohyo> chList = cuService.findAllChohyo();
//
//	List<ChohyoDto> chListEdit = new ArrayList<>();
//
//	//複合キーの設定
//	Function<Chohyo, String> compositeKey = chh -> {
//		StringBuffer sb = new StringBuffer();
//		sb.append(chh.getHeader1()).append("-").append(chh.getHeader2()).append("-").append(chh.getHeader3());
//		return sb.toString();
//	};
//
//	//ヘッダーのグルーピング
//	TreeMap<String, List<Chohyo>> grpByKey = chList.stream().collect(Collectors.groupingBy(compositeKey,
//			TreeMap<String, List<Chohyo>>::new, Collectors.mapping(u -> u, Collectors.toList())));
//
//	TreeMap<String, BigDecimal> sumMap = new TreeMap<>();
//
//	//マップを展開
//	for (String key : grpByKey.keySet()) {
//		System.out.println(grpByKey.get(key));
//		//グループの合計
//		sumMap.put(key,
//				grpByKey.get(key).stream().map(Chohyo::getMeisai1).reduce(BigDecimal.ZERO, BigDecimal::add));
//	}
//
//	//それぞれのグループの合計値を出す
//	System.out.println("grpByKey :" + grpByKey);
//	System.out.println("sumMap :" + sumMap);
//	//キーはなんだろ
//	for (int i = 0; i < chList.size(); i++) {
//		Chohyo ch = new Chohyo();
//		ch.setHeader1(chList.get(i).getHeader1());
//		ch.setHeader2(chList.get(i).getHeader2());
//		ch.setHeader3(chList.get(i).getHeader3());
//
//		StringBuffer sb = new StringBuffer();
//		sb.append(ch.getHeader1()).append("-").append(ch.getHeader2()).append("-").append(ch.getHeader3());
//		ch.setGoukei1(sumMap.get(sb.toString()));
////		chListEdit.add(ch);
//	}
//	
//	System.out.println("chListEdit :" + chListEdit);
//	//listに入れて終わり
//
//}

//@GetMapping("/index")
//public String index(Model model) {
//  String sql = "SELECT * FROM customer";
//  List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//  System.out.println(list);
//          model.addAttribute("testList", list);
//  return "index";
//}
