package jp.co.addon.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Fundmst {
	private int id;
	private String fund1;
	private String fund2;
	private BigDecimal hakabu;
	private String header1;
	private String header2;
}
