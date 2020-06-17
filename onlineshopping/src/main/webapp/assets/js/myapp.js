$(function() {
	switch (menu) {
	case 'About Us':$('#about').addClass('active');	break;
	
	case 'Contact Us':$('#contact').addClass('active');break;
	
	case 'All Products':$('#listProducts').addClass('active');break;
	
	case 'Manage Products':$('#manageProducts').addClass('active');break;
	
	case 'Shopping Cart':$('#userCart').addClass('active');break;

	
	default:
		if(menu == "Home") break;
		$('#listProducts').addClass('active');
	        $('#a_'+menu).addClass('active');
	break;
	       
	
	}
	
	
	
	// for handling CSRF token
	var token = $('meta[name="_csrf"]').attr('content');
	var header = $('meta[name="_csrf_header"]').attr('content');
	
	if((token!=undefined && header !=undefined) && (token.length > 0 && header.length > 0)) {		
		// set the token header for the ajax request
		$(document).ajaxSend(function(e, xhr, options) {			
			xhr.setRequestHeader(header,token);			
		});				
	}
	
	
	
	
	// for adding a loader
	$(window).load(function(){
		setTimeout(function() {
			$(".se-pre-con").fadeOut("slow");
		}, 50);			
	});
	
	
	
	
	function errorPlacement(error, element) {
		// Add the 'help-block' class to the error element
		error.addClass("help-block");
		
		// add the error label after the input element
		error.insertAfter(element);
		
		
		// add the has-feedback class to the
		// parent div.validate in order to add icons to inputs
		element.parents(".validate").addClass("has-feedback");	

	}
	
	
	var $table = $('#productListTable');
	
	if($table.length){
		
		var jsonUrl="";
		if(window.categoryId == ''){
			jsonUrl = window.contextRoot + '/json/data/all/products';
		}else{
			jsonUrl = window.contextRoot + '/json/data/category/' +window.categoryId+'/products';
		}
		
		$table.DataTable({
			lengthMenu:[[3,5,7,10,-1],['3 Records','5 Records','7 Records','10 Records','All Records']],
		    pageLength:5,
			
		    ajax:{
		    	   url:jsonUrl,
		    	   dataSrc:''
		           
		    },
		    
		    columns:[
		    	
		    	     {
		    	    	 data:'code',
		    	         mRender:function(data,type,row){
		    	        	 return '<img src="'+window.contextRoot+'/resources/images/'+data+'.jpg" class="dataTableImg" />';
		    	         }
		    	     },
		    	     {
		    	    	 data:'name'
		    	     },
		    	     {
		    	    	 data:'brand'
		    	     },
		    	     {
		    	    	 data:'unitPrice',
		    	    	 mRender:function(data,type,row){
		    	    		 return '&#36;'+ data
		    	    	 }
		    	     },
		    	     {
		    	    	 data:'quantity',
		    	    	 mRender:function(data,type,row){
		    	    		 
			    	             if(data < 1){
			    	    			 return '<span style="color:red">Out Of Stock!</span>'
			    	    		 }
			    	    		 
			    	    		 return data;
			    	    } 
		    	     },
		    	     {
		    	    	 data:'id',
		    	    	 bSortable:false,
		    	    	 mRender:function(data,type,row){
		    	    		 var str='';
		    	    		 str += '<a href=" '+window.contextRoot+'/show/'+data+'/product" class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span></a> &#160;';
		    	    		 
		    	    		 if(userRole=="ADMIN"){
		    	    			 str += '<a href=" '+window.contextRoot+'/manage/'+data+'/product" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span></a> &#160;';
		    	    		 }else{
		    	    		 
			    	    		 if(row.quantity < 1){
			    	    			 str += '<a href="javascript:void(0)" class="btn btn-success disabled"><span class="glyphicon glyphicon-shopping-cart"></span></a> &#160;';
			    	    		 }else{
				    	    			 str += '<a href=" '+window.contextRoot+'/cart/add/'+data+'/product" class="btn btn-success"><span class="glyphicon glyphicon-shopping-cart"></span></a> &#160;';
			    	    		 
			    	    		 }
		    	    		
		    	    		 }
		    	    		 return str
		    	    	 }
		    	     }
		    ]
		
		})
		
	}
	
	var $alert=$('.alert')
	
	if($alert.length){
		 setTimeout(function(){
			$alert.fadeOut('slow');
		}, 3000);
	}
	
	
	
	
	
	var $adminProductsTable = $('#adminProductsTable')
	
	if($adminProductsTable.length){
		
		var jsonUrl =   window.contextRoot+'/json/data/admin/all/products';
		
		$adminProductsTable.DataTable({
			
			lengthMenu : [[10,20,30,-1],['10 Records','20 Records','30 Records','All Prodcuts']],
			pageLength : 10,
			ajax       : {
				url : jsonUrl ,
				dataSrc : ''
			},
			columns   : [
				         
				         {
				        	 data : 'id'
				         },
				         {
				        	 data : 'code',
				        	 bSortable : false ,
				        	 mRender   : function(data,type,row){
				        		 
				        		 return '<img src = " '+window.contextRoot+'/resources/images/'+data+'.jpg"  class="adminDataTableImg"/>'
				        		 
				        	 }
				         },
				         {
				        	 data : 'name'
				         },
				         {
				        	 data : 'brand'
				         },
				         {
				        	 data: 'quantity',
				        	 mRender : function(data,type,row){
				        		 if(data < 1 ){
				        			 return '<span style="color:red">Out Of Stock!</span>'
				        		 }
				        		 return data
				        	 }
				         },
				         {
				        	 data : 'unitPrice',
				        	 mRender : function(data,type,row){
				        		 return '&#36;'+ data
				        	 }
				         },
				         {
				        	 data : 'active',
				        	 bSortable : false,
				        	 mRender : function(data,type,row){
				        		 
				        		 var str = '';
				        		 str    += '<label class="switch">';
				        		 if(data){
				        			 str += '<input type="checkbox" checked="checked" value="'+row.id+'" />';
				        				 
				        		 }else{
				        			 str += '<input type="checkbox" value="'+row.id+'" />';
				        		 }
				        		 
				        		 str += '<div class="slider"></div></label>';
				        		 
				        		 return str;
				        	 }
				         },
				         {
				        	 data : 'id',
				        	 bSortable : false ,
				        	 mRender : function(data,type,row){
				        		 var str = '';
				        		  str += '<a href =" '+window.contextRoot+'/manage/'+data+'/product" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span></a>';
				        		  return str
				        	 }
				         }	         				
			],
			
			initComplete:function(){
				var api = this.api();
				api.$('.switch input[type="checkbox"]').on('change',function(){
					var checkbox=$(this);
					var checked=checkbox.prop('checked');
					var dMsg=(checked)? 'You want to activate the product?':
						                 'You want to deactivate the product?';
					var value=checkbox.prop('value');
					
					bootbox.confirm({
						size:'medium',
						title:'Product Activation & Deactivation',
						message:dMsg,
						callback:function(confirmed){
							if(confirmed){
								console.log(value);
								
								var activationUrl = window.contextRoot+'/manage/product/'+ value +'/activation';
								
								$.post(activationUrl,function(data){
									bootbox.alert({
										size:'medium',
										title:'Information',
										message:data
									});
								})
								
							}else{
								checkbox.prop('checked',!checked);
							}
						}
						
						
					})	
					
				})
				
			}
		
		   
		})
	}
	
	
	
	
	var $categoryForm = $('#categoryForm');
	
	if($categoryForm.length){
		
		$categoryForm.validate({
			rules:{
				name:{
					required:true,
					minlength:3
				},
				description:{
					required:true,
					minlength:3
				}
			},
			message:{
				name:{
					required: 'Please enter product name!',
					minlength: 'Please enter at least three characters'
				},
				description:{
					required: 'Please enter product name!',
					minlength: 'Please enter at least three characters'
				}
				
			},
			errorElement:"em",
			errorPlacement:function(error,element){
				error.addClass('help-block');
				error.insertAfter(element);
			}
		})
	}
	
	
	
	
	
var $loginForm = $('#loginForm');
	
	if($loginForm.length){
		
		$loginForm.validate({
			rules:{
				username:{
					required:true,
					email:true
				},
				password:{
					required:true,
				}
			},
			message:{
				username:{
					required: 'Please enter the username!',
					email: 'Please enter the valid email!'
				},
				password:{
					required: 'Please enter password!',
					
				}
				
			},
			errorElement:"em",
			errorPlacement:function(error,element){
				error.addClass('help-block');
				error.insertAfter(element);
			}
		})
	}
	
	
	/* handle refresh cart*/	
	$('button[name="refreshCart"]').click(function(){
		var cartLineId = $(this).attr('value');
		var countField = $('#count_' + cartLineId);
		var originalCount = countField.attr('value');
		// do the checking only the count has changed
		if(countField.val() !== originalCount) {	
			// check if the quantity is within the specified range
			if(countField.val() < 1 || countField.val() > 3) {
				// set the field back to the original field
				countField.val(originalCount);
				bootbox.alert({
					size: 'medium',
			    	title: 'Error',
			    	message: 'Product Count should be minimum 1 and maximum 3!'
				});
			}
			else {
				// use the window.location.href property to send the request to the server
				var updateUrl = window.contextRoot + '/cart/' + cartLineId + '/update?count=' + countField.val();
				window.location.href = updateUrl;
			}
		}
	});	
	
	
	
	
	
	
})