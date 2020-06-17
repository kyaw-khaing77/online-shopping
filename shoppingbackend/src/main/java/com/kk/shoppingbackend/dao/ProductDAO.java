package com.kk.shoppingbackend.dao;

import java.util.List;

import com.kk.shoppingbackend.dto.Product;

public interface ProductDAO {

	
	public Product getProductById(int id);
	
	public boolean addProduct(Product product);
	
	public boolean updateProduct(Product product);
	
	public boolean deleteProduct(Product product);
	
	public List<Product> getProductsList();
	
	public List<Product> getActiveProducts();
	
	public List<Product> getActiveProductsByCategory(int categoryId);
	
	public List<Product> getLatestActiveProducts(int count);
	
	
	
}
