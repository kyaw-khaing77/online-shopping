package com.kk.onlineshopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kk.shoppingbackend.dao.ProductDAO;
import com.kk.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/json/data")
public class JsonDataController {

	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getAllProducts(){
		return productDAO.getActiveProducts();
	}
	
	
	@RequestMapping("admin/all/products")
	@ResponseBody
	public List<Product> getAdminAllProducts(){
		return productDAO.getProductsList();
	}
	
	
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getAllProductsByCategory(@PathVariable("id") int id){
		return productDAO.getActiveProductsByCategory(id);
	}
}
