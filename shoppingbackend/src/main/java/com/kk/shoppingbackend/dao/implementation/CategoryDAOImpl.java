package com.kk.shoppingbackend.dao.implementation;


import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kk.shoppingbackend.dao.CategoryDAO;
import com.kk.shoppingbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {


	@Autowired
	 private SessionFactory sessionFactory;
	
	@Override
	public List<Category> categoryList() {
		
		String activeCategory = "FROM Category WHERE active = :active";
		
		Query query = sessionFactory.getCurrentSession().createQuery(activeCategory);
		
		query.setParameter("active", true);
		
		return query.getResultList();
	}

	@Override
	public Category getCategoryById(int id) {

		return sessionFactory.getCurrentSession().get(Category.class,id);
	}

	@Override
	public boolean addCategory(Category category) {
		try {
			sessionFactory.getCurrentSession().persist(category);
			return true;
		} catch (Exception e) {
           e.printStackTrace();
           return false;
		}
		
	}

	@Override
	public boolean updateCategory(Category category) {
		try {
			sessionFactory.getCurrentSession().update(category);
			return true;
		} catch (Exception e) {
           e.printStackTrace();
           return false;
		}
	}

	@Override
	public boolean deleteCategory(Category category) {
		  category.setActive(false);
		try {
			sessionFactory.getCurrentSession().update(category);
			return true;
		} catch (Exception e) {
           e.printStackTrace();
           return false;
		}
	}

}
