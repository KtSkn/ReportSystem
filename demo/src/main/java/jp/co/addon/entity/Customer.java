package jp.co.addon.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Customer {
	private int id;
	private String name;
	private int old;
	private BigDecimal yukokabu;
}
