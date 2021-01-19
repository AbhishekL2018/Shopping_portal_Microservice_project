package com.firedev.product.service;

import com.firedev.product.controller.ProductController;
import com.firedev.product.dto.Product;
import com.firedev.product.exception.CurrencyNotValidException;
import com.firedev.product.exception.OfferNotValidException;
import com.firedev.product.repository.ProductRepository;
import com.firedev.product.service.config.ProductConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConfiguration productConfiguration;

    public String addProduct(Product product){
        if(product.getPrice() == 0 && product.getDiscount()>0){
            throw new OfferNotValidException("No discount is allowed at 0 product price");
        }
        List<String> validCurrencies = productConfiguration.getCurrencies();
        if(!validCurrencies.contains(product.getCurrency().toUpperCase())){
            logger.info(product.getCurrency().toUpperCase());
            throw new CurrencyNotValidException("Invalid currency --- availale currencies -> "+validCurrencies);
        }
        productRepository.save(product);
        return "success";
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> productCategoryList(String category) {
        return productRepository.findByCategory(category);
    }

    public Product productById(String id) {
        return productRepository.findById(id).get();
    }

    public String updateProduct(Product newProduct) {
        productRepository.save(newProduct);
        return "product updated failed";
    }

    public String deleteProductById(String id) {
        productRepository.deleteById(id);
        return "deleted successfully";
    }
}
