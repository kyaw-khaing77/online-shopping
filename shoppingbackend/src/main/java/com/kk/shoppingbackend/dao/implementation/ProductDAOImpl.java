package com.kk.shoppingbackend.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kk.shoppingbackend.dao.ProductDAO;
import com.kk.shoppingbackend.dto.Product;


@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Product getProductById(int id) {
          try {

      		return sessionFactory
      				 .getCurrentSession()
      				     .get(Product.class, id);
		} catch (Exception e) {
                e.printStackTrace();
                return null;
		}
	}

	@Override
	public boolean addProduct(Product product) {
		try {
			  sessionFactory
			     .getCurrentSession()
			         .persist(product);
			  return true;
		} catch (Exception e) {
              e.printStackTrace();
              return false;
		}
	}

	@Override
	public boolean updateProduct(Product product) {
		try {
			  sessionFactory
			      .getCurrentSession()
			          .update(product);
			  
			  return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteProduct(Product product) {
		try {
			   product.setActive(false);
			   sessionFactory
			         .getCurrentSession()
			             .update(product);
			   return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Product> getProductsList() {
		
		return sessionFactory
				    .getCurrentSession()
				        .createQuery("From Product",Product.class)
				            .getResultList();
	}

	@Override
	public List<Product> getActiveProducts() {
		   return  sessionFactory
				      .getCurrentSession()
				         .createQuery("FROM Product WHERE active = :active" ,Product.class)
				            .setParameter("active", true)
				               .getResultList();
	}

	@Override
	public List<Product> getActiveProductsByCategory(int categoryId) {
	       
		   return sessionFactory
				      .getCurrentSession()
				         .createQuery("From Product Where active = :active And categoryId = :categoryId ",Product.class)
				             .setParameter("active", true)
				                .setParameter("categoryId", categoryId )
				                    .getResultList();
	}

	@Override
	public List<Product> getLatestActiveProducts(int count) {
		   
		return sessionFactory
				   .getCurrentSession()
				      .createQuery("From Product Where active = :active Order By id ",Product.class)
				         .setParameter("active", true)
				            .setFirstResult(0)
				               .setMaxResults(count)
				                  .getResultList();
	}

}
