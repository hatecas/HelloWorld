		
		
		function clearCart() {
			$.ajax({
		        type: 'POST',
		        url: "/store/clearCart",
		        contentType: 'application/json',
		        success: function () {
		            scrollToBottom();
		            loadCart();
		        },
		        error: function () {
		            console.error('Clear cart failed');
		        }
		    });
		}
		
		function loadCart() {
            $.ajax({
                type: 'GET',
                url: "/store/loadCart",
                success: function (data) {
                    updateCart(data);
                },
                error: function () {
                    console.error('Ajax request failed');
                }
            });
        }

        function updateCart(cartItems) {
        	 const cartTableBody = $('#cart-list tbody');
        	 cartTableBody.empty();
        	 
            cartItems.forEach(function (item) {
            	const imagePath = "/resources/images/store/storeDotoriIcon.png";
            	const row = '<tr><td>' + item.cate + '</td><td>' + item.name + '</td><td><img id="dotoriIcon" src="' + imagePath + '" alt="' + item.name + '">' + item.price + '</td></tr>';
                cartTableBody.append(row);
            });

            scrollToBottom();
        }
		
        function buyCart() {
            var result = confirm('정말로 구매하시겠습니까?');
            
            if (result) {
                $.ajax({
                    type: 'POST',
                    async: 'false',
                    url: '/store/buyCart',
                    success: function(msg) {
                        console.log(msg);
                        if (msg.success) {
                            alert(msg.message);
                            updateDotoriCount();
                            
                            clearCart();
                        } else {
                            alert(msg.message);
                            clearCart();
                        }
                    },
                    error: function(error) {
                        console.error(error);
                    }
                });
            } else {
                alert('구매가 취소되었습니다.');
            }
        }
        
        function updateDotoriCount() {
            $.ajax({
                type: 'POST',
                url: '/store/updateDotoriCount',
                data: JSON.stringify(),
                success: function (result) {
                	if (result !== undefined) {
                        $('#userDotoriCnt').text(result);
                    }
                },
                error: function (error) {
                    console.error('도토리 개수 업데이트 실패');
                }
            });
        };
        
		function scrollToBottom() {
		    const cartList = document.querySelector('.cart-list-over');
		    cartList.scrollTop = cartList.scrollHeight;
		}
		
		function loadPage(page) {
	        $('#page').val(page);
	        $('#frm1').submit();
	    }
	
	    $(document).ready(function () {
	        loadCart();
	
	        $('.spanPage').on('click', function(){
	            loadPage($(this).data('page'));
	        });
	        
	        $('.divOneProduct').on('click', function () {
	        	const productCate = $(this).data('product-cate');
	            const productTableCate = $(this).data('product-table-cate');
	            const productContentPath = $(this).data('product-contentpath');
	            const productName = $(this).data('product-name');
	            const productPrice = $(this).data('product-price');
				
	            addToCart(productCate, productTableCate, productContentPath, productName, productPrice);
	        });
	        
	        $('.product').on('click', function () {
	            const productCate = $(this).data('product-cate');
	            const productTableCate = $(this).data('product-table-cate');
	            const productContentPath = $(this).data('product-contentpath');
	            const productName = $(this).data('product-name');
	            const productPrice = $(this).data('product-price');
				
	            addToCart(productCate, productTableCate,  productName, productContentPath, productPrice);
	        });
	
	        function addToCart(cate, tableCate, name, contentPath, price) {
	            $.ajax({
	                type: 'POST',
	                url: "/store/addToCart",
	                contentType: 'application/json',
	                data: JSON.stringify({ cate: cate, tableCate: tableCate, name: name,
	                					contentPath: contentPath, price: price }),
	                success: function () {
	                    loadCart();
	                },
	                error: function () {
	                    console.error('Ajax request failed');
	                }
	            });
	        }
	        
	    });