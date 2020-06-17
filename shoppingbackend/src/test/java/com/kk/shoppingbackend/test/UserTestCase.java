package com.kk.shoppingbackend.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.kk.shoppingbackend.dao.UserDAO;
import com.kk.shoppingbackend.dto.Address;
import com.kk.shoppingbackend.dto.Cart;
import com.kk.shoppingbackend.dto.User;

public class UserTestCase {

	private static AnnotationConfigApplicationContext context;
	private static UserDAO userDAO;
	private User user = null;
	private Address address = null;
	private Cart cart = null;
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.kk.shoppingbackend");
		context.refresh();
		userDAO = (UserDAO) context.getBean("userDAO");
	}
	
//
//	@Test
//	public void testAddUser() {
//		
//		user = new User() ;
//		user.setFirstName("Hrithik");
//		user.setLastName("Roshan");
//		user.setEmail("hr@gmail.com");
//		user.setContactNumber("1234512345");
//		user.setRole("CUSTOMER");
//		user.setEnabled(true);
//		user.setPassword("12345");
//		
//		
//		address = new Address();
//		address.setAddressLineOne("101/B Jadoo Society, Krissh Nagar");
//		address.setAddressLineTwo("Near Kaabil Store");
//		address.setCity("Mumbai");
//		address.setState("Maharashtra");
//		address.setCountry("India");
//		address.setPostalCode("400001");
//		address.setBilling(true);
//		
//		cart = new Cart();
//		
//		// linked the address with the user
//		address.setUserId(user.getId());
//		
//		// linked the cart with the user
//		cart.setUser(user);
//		// link the user with the cart
//		user.setCart(cart);
//		
//		// add the user
//		assertEquals("Failed to add the user!", true, userDAO.addUser(user));	
//		// add the address
//		assertEquals("Failed to add the billing address!", true, userDAO.addAddress(address));
//
//				
//		// add the shipping address
//		address = new Address();
//		address.setAddressLineOne("201/B Jadoo Society, Kishan Kanhaiya Nagar");
//		address.setAddressLineTwo("Near Kudrat Store");
//		address.setCity("Mumbai");
//		address.setState("Maharashtra");
//		address.setCountry("India");
//		address.setPostalCode("400001");
//		address.setUserId(user.getId());
//		assertEquals("Failed to add the shipping address!", true, userDAO.addAddress(address));
//		
//	}
//	

	// working for uni-directional
/*
	@Test
	public void testAddAddress() {
		user = userDAO.get(1);
		
		address = new Address();
		address.setAddressLineOne("301/B Jadoo Society, King Uncle Nagar");
		address.setAddressLineTwo("Near Store");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setPostalCode("400001");
				
		address.setUser(user);
		// add the address
		assertEquals("Failed to add the address!", true, userDAO.addAddress(address));	
	}
	
	@Test
	public void testUpdateCart() {
		user = userDAO.get(1);
		cart = user.getCart();
		cart.setGrandTotal(10000);
		cart.setCartLines(1);
		assertEquals("Failed to update the cart!", true, userDAO.updateCart(cart));			
	} 

*/
	
//	@Test
//	public void testUpdateCart() {
//		user = userDAO.getUserByEmail("hr@gmail.com");
//		assertEquals("Update Cart failed", "Roshan" , user.getLastName());
//		cart = user.getCart();
//		cart.setCartLines(2);
//		cart.setGrandTotal(5555);
//		assertEquals("Update cart Failed", true , userDAO.updateCart(cart));
//	}

	@Test
	public void testGetAddress() {
		user = userDAO.getUserByEmail("hr@gmail.com");
		address = new Address();
		address.setAddressLineOne("301/B Jadoo Society, King Uncle Nagar");
		address.setAddressLineTwo("Near Store");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setCountry("India");
		address.setPostalCode("400001");
		address.setUser(user);
		address.setBilling(true);
		userDAO.addAddress(address);
		
		assertEquals("Get shipping address failed", 1, userDAO.listShippingAddress(user).size());
		assertEquals("Get billing address failed", 1, userDAO.listBillingAddress(user).size());
	}
}
