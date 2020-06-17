package com.kk.shoppingbackend.dao;

import java.util.List;

import com.kk.shoppingbackend.dto.Category;

public interface CategoryDAO {

	public List<Category> categoryList();
	
	public Category getCategoryById(int id);
	
	public boolean addCategory(Category category);
	
	public boolean updateCategory(Category category);
	
	public boolean deleteCategory(Category category);
}
