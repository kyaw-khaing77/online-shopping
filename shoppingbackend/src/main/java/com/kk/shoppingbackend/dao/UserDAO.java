package com.kk.shoppingbackend.dao;


import java.util.List;

import com.kk.shoppingbackend.dto.Address;
import com.kk.shoppingbackend.dto.Cart;
import com.kk.shoppingbackend.dto.User;

public interface UserDAO {

	boolean addUser(User user);
	User getUserByEmail(String email);
	boolean addAddress(Address address);
	boolean updateAddress(Address address);
	List<Address> listShippingAddress(User user);
	List<Address> listBillingAddress(User user);
}
