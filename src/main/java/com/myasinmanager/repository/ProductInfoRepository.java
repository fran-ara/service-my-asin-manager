package com.myasinmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myasinmanager.model.ProductInfo;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class ProductInfoRepository {

	@Autowired
	private DynamoDbEnhancedClient dynamoDbenhancedClient;

	public void save(final ProductInfo order) {
		DynamoDbTable<ProductInfo> orderTable = getTable();
		orderTable.putItem(order);
	}
	
	  public ProductInfo findById(final String productId) {
	    DynamoDbTable<ProductInfo> orderTable = getTable();
	    Key key = Key.builder().partitionValue(productId)
	                       .build();
	    
	    ProductInfo product = orderTable.getItem(key);
	    
	    return product;
	  }


	private DynamoDbTable<ProductInfo> getTable() {
		DynamoDbTable<ProductInfo> orderTable = dynamoDbenhancedClient.table("ProductInfo",
				TableSchema.fromBean(ProductInfo.class));
		return orderTable;
	}

}