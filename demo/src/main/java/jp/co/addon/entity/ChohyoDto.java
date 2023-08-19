package jp.co.addon.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ChohyoDto {

	private int id;
	private String header1;
	private String header2;
	private String header3;
	private String meisai1;
	private BigDecimal meisai2;
	private BigDecimal meisai3;
	private BigDecimal goukei1;
	private BigDecimal goukei2;
	private BigDecimal goukei3;
	
	private int page;
}
