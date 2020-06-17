package com.kk.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kk.shoppingbackend.dao.CategoryDAO;
import com.kk.shoppingbackend.dto.Category;

public class CategoryTestCase {

	public static AnnotationConfigApplicationContext context;

	public static CategoryDAO categoryDAO;

	public Category category;

	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();

		context.scan("com.kk.shoppingbackend");

		context.refresh();

		categoryDAO = (CategoryDAO) context.getBean("categoryDAO");
	}

//	@Test 
//	public void testAddCategory() {
//		category = new Category();
//		
//		category.setName("Phone");
//		category.setDescription("This is some description for phone");
//		category.setImageUrl("CAT_1.png");
//		
//		assertEquals("Successfully added Category",true,categoryDAO.addCategory(category));	
//		
//	}

//	@Test
//	public void testGetCategory() {
//		category = categoryDAO.getCategoryById(2);
//		assertEquals("Successfully get single category","Phone",category.getName());	
//	}

//	@Test
//	public void testUpdateCategory() {
//		category = categoryDAO.getCategoryById(1);
//		assertEquals("Successfully delete category",true,categoryDAO.deleteCategory(category));	
//	}

	@Test
	public void testGetCategoryList() {
		
		assertEquals("Successfully get category list",3,categoryDAO.categoryList().size());	
	}

//	@Test
//	public void testCRUDCategory() {
//
//		// add operation
//		category = new Category();
//
//		category.setName("Laptop");
//		category.setDescription("This is some description for laptop!");
//		category.setImageUrl("CAT_3.png");
//
//		assertEquals("Successfully added a category inside the table!", true, categoryDAO.addCategory(category));
//
//		category = new Category();
//
//		category.setName("Tablet");
//		category.setDescription("This is some description for tablet!");
//		category.setImageUrl("CAT_4.png");
//
//		assertEquals("Successfully added a category inside the table!", true, categoryDAO.addCategory(category));
//
//		// fetching and updating the category
//		category = categoryDAO.getCategoryById(1);
//
//		category.setName("PC");
//
//		assertEquals("Successfully updated a single category in the table!", true,
//				categoryDAO.updateCategory(category));
//
//		// delete the category
//		assertEquals("Successfully deleted a single category in the table!", true,
//				categoryDAO.deleteCategory(category));
//
//		// fetching the list
//		assertEquals("Successfully fetched the list of categories from the table!", 1,
//				categoryDAO.categoryList().size());
//
//	}

}
