package com.kk.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kk.shoppingbackend.dao.ProductDAO;
import com.kk.shoppingbackend.dto.Product;

public class ProductTestCase {

	private static AnnotationConfigApplicationContext context;
	
	
	private static com.kk.shoppingbackend.dao.ProductDAO productDAO;
	
	
	private Product product;
	
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.kk.shoppingbackend");
		context.refresh();
		productDAO = (ProductDAO)context.getBean("productDAO");
	}
	
	@Test
	public void testCRUDProduct() {
		
		// create operation
		product = new Product();
				
		product.setName("Oppo Selfie S53");
		product.setBrand("Oppo");
		product.setDescription("This is some description for oppo mobile phones!");
		product.setUnitPrice(25000);
		product.setActive(true);
		product.setCategoryId(3);
		product.setSupplierId(3);
		
		assertEquals("Something went wrong while inserting a new product!",
				true,productDAO.addProduct(product));		
		
		
		// reading and updating the category
		product = productDAO.getProductById(2);
		product.setName("Samsung Galaxy S7");
		assertEquals("Something went wrong while updating the existing record!",
				true,productDAO.updateProduct(product));		
				
		assertEquals("Something went wrong while deleting the existing record!",
				true,productDAO.deleteProduct(product));		
		
		// list
		assertEquals("Something went wrong while fetching the list of products!",
				6,productDAO.getProductsList().size());		
				
	}
		
//	
//	@Test
//	public void testListActiveProducts() {
//		assertEquals("Something went wrong while fetching the list of products!",
//				5,productDAO.getActiveProducts().size());				
//	} 
//	
//	
//	@Test
//	public void testListActiveProductsByCategory() {
//		assertEquals("Something went wrong while fetching the list of products!",
//				3,productDAO.getActiveProductsByCategory(3).size());
//		assertEquals("Something went wrong while fetching the list of products!",
//				2,productDAO.getLatestActiveProducts(1).size());
//	} 
//	
//	@Test
//	public void testGetLatestActiveProduct() {
//		assertEquals("Something went wrong while fetching the list of products!",
//				3,productDAO.getLatestActiveProducts(3).size());
//		
//	} 
//	
	
	
		
}
