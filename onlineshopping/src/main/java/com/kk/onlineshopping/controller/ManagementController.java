package com.kk.onlineshopping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kk.onlineshopping.util.FileUploadUtility;
import com.kk.onlineshopping.validator.ProductValidator;
import com.kk.shoppingbackend.dao.CategoryDAO;
import com.kk.shoppingbackend.dao.ProductDAO;
import com.kk.shoppingbackend.dto.Category;
import com.kk.shoppingbackend.dto.Product;

@Controller
@RequestMapping("/manage")
public class ManagementController {
	
	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value="/products",method=RequestMethod.GET)
	public ModelAndView manageProduct(@RequestParam(name="operation",required = false) String op) {
		ModelAndView mv = new ModelAndView("page");
		
        Product nProduct = new Product();
		
		nProduct.setSupplierId(1);
		nProduct.setActive(true);
        
		
		if(op!=null) {
			
			if(op.equals("product")) {
				mv.addObject("message","Product Submitted Successfully");
			}
			else if(op.equals("category")) {
				mv.addObject("message","Category Submitted Successfully");
			}
		}
		mv.addObject("product", nProduct);
		
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");		
		return mv;
				
		
	}
	
	@RequestMapping(value="/{id}/product",method=RequestMethod.GET)
	public ModelAndView manageEditProduct(@PathVariable int id) {
		ModelAndView mv = new ModelAndView("page");
		
        Product nProduct = productDAO.getProductById(id);		
		
		mv.addObject("product", nProduct);
		
		mv.addObject("userClickManageProducts", true);
		mv.addObject("title", "Manage Products");		
		return mv;			
		
	}
	
	
	
	@RequestMapping(value="/product/{id}/activation",method=RequestMethod.POST)
	@ResponseBody
	public String handleProductActivation(@PathVariable int id) {
		
		Product product = productDAO.getProductById(id);
		
		boolean isActive = product.isActive();
		product.setActive(!isActive);
		
		productDAO.updateProduct(product);
		return (isActive)? "You have successfully deactivated the product " +product.getName() + "( Id is " +product.getId() + ")":
			               "You have successfully activated the product " +product.getName() + "( Id is " +product.getId() + ")";
	}
	
	
	
	@RequestMapping(value="/category",method=RequestMethod.POST)
	public String handleCategorySubmission(@ModelAttribute Category category) {
		
		categoryDAO.addCategory(category);
		
		return "redirect:/manage/products?operation=category";
		
	}
	
	
	
	
	
	
	@ModelAttribute("categories")
	public List<Category> getCategories(){
		return categoryDAO.categoryList();
	}
	
	@ModelAttribute("category")
	public Category getCategory(){
		return new Category();
	}
	
	
	@RequestMapping(value="/products",method=RequestMethod.POST)
	public String handleProductSubmission(@Valid @ModelAttribute("product") Product mproduct,BindingResult results,Model model,
			HttpServletRequest req) {
		
		if(mproduct.getId() == 0) {
		    new ProductValidator().validate(mproduct, results);
		}else {
			if(!mproduct.getFile().getOriginalFilename().equals("")) {
				new ProductValidator().validate(mproduct, results);
			}
		}
		if(results.hasErrors()) {
			model.addAttribute("userClickManageProducts", true);
			model.addAttribute("title", "Manage Products");	
			model.addAttribute("message", "Validation failed for product submission!");
			
			return"page";
		}
		
		logger.info(mproduct.toString());
		
		if(mproduct.getId() == 0) {
		    productDAO.addProduct(mproduct);
		}else {
			productDAO.updateProduct(mproduct);
		}
		if(!mproduct.getFile().getOriginalFilename().equals("")) {
			FileUploadUtility.uploadFile(req,mproduct.getFile(),mproduct.getCode());
		}
		
		
		return "redirect:/manage/products?operation=product";
	}
}
