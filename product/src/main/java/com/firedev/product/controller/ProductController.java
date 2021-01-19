package com.firedev.product.controller;

import com.firedev.product.dto.Product;
import com.firedev.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping("/addProduct")
    ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
        String status = productService.addProduct(product);
        logger.info("product added status --> {}"+status);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/productList")
    List<Product> productList(){
        return productService.listAllProducts();
    }

    @GetMapping("/product/{category}")
    List<Product> productCategoryList(@PathVariable("category") String category){
        return productService.productCategoryList(category);
    }

    @GetMapping("/productList/{Id}")
    Product productById(@PathVariable("Id") String id){
        return productService.productById(id);
    }

    @PutMapping("/productUpdate")
    String updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @DeleteMapping("/productList/{Id}")
    String deleteProductById(@PathVariable("Id") String id){
        return productService.deleteProductById(id);
    }

}
