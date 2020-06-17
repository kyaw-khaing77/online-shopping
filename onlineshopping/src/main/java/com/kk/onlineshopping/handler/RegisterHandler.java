package com.kk.onlineshopping.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kk.onlineshopping.model.RegisterModel;
import com.kk.shoppingbackend.dao.UserDAO;
import com.kk.shoppingbackend.dto.Address;
import com.kk.shoppingbackend.dto.Cart;
import com.kk.shoppingbackend.dto.User;

@Component
public class RegisterHandler {

	@Autowired
	private UserDAO userDAO;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
		

 public RegisterModel init() { 
  return new RegisterModel();
 } 
 
 public void addUser(RegisterModel registerModel, User user) {
	  registerModel.setUser(user);
 } 
 public void addBilling(RegisterModel registerModel, Address billing) {
	  registerModel.setBilling(billing);
 }
 
 public String saveAll(RegisterModel registerModel) {
	  String transitionValue = "success";
	  User user = registerModel.getUser();
	  if(user.getRole().equals("USER")) {
	   // create a new cart
	   Cart cart = new Cart();
	   cart.setUser(user);
	   user.setCart(cart);
	  }
	     
	  user.setPassword(passwordEncoder.encode(user.getPassword()));
	  // save the user
	  userDAO.addUser(user);
	  // save the billing address
	  Address billing = registerModel.getBilling();
	  billing.setUser(user);
	  billing.setBilling(true);  
	  userDAO.addAddress(billing);
	  return transitionValue ;
	 } 
 
 public String validateUser(User user, MessageContext error) {
	  String transitionValue = "success";
	   if(!user.getPassword().equals(user.getConfirmPassword())) {
	    error.addMessage(new MessageBuilder()
	    		            .error()
	    		            .source("confirmPassword")
	    		            .defaultText("Password does not match confirm password!")
	    		            .build());
	    
	    transitionValue = "failure";  
	    
	   }  
	   
	   if(userDAO.getUserByEmail(user.getEmail())!=null) {
	    error.addMessage(new MessageBuilder()
	    		            .error()
	    		            .source("email")
	    		            .defaultText("Email address is already taken!")
	    		            .build());
	    
	    transitionValue = "failure";
	   }
	  return transitionValue;
	 }

}
