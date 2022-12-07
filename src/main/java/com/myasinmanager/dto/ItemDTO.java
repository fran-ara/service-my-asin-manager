package com.myasinmanager.dto;

import java.util.List;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemDTO {
	
	private String asin;
	private Attributes attributes;
	
	@Data
	@ToString
	public class Attributes{
		private List<ListPrices> listPrices;
	}
	
	@Data
	@ToString
	public class ListPrices{
		private String price;
	}
	
	
}
