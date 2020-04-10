package io.gusraccoon.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gusraccoon.entity.Product;
import io.gusraccoon.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getProducts() {
    	System.out.println("Get Request /api/products/");
        List<Product> productList = productService.getProducts();
        System.out.println("productList : "+productList.toString());
        //System.out.println("productList : "+productList);
        Iterator it = productList.iterator();
        while(it.hasNext()) {
        	//String element = (String) it.next();
        	//System.out.println("element : "+element);
        	//System.out.println(it.next());
        	Product p = (Product) it.next();
        	System.out.println(p.toString());
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    @GetMapping("/master")
    public ResponseEntity<?> getProductsFromMaster() {
    	System.out.println("Get Request /api/products/master");
        List<Product> productList = productService.getProductsMaster();
        
        System.out.println("productList : "+productList.toString());
        //System.out.println("productList : "+productList);
        
        Iterator it = productList.iterator();
        while(it.hasNext()) {
//        	String element = (String) it.next();
//        	System.out.println("master element : "+element);
        	Product p = (Product) it.next();
        	System.out.println(p.toString());
        }
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}