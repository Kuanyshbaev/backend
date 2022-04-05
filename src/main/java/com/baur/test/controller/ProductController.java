package com.baur.test.controller;

import com.baur.test.model.Product;
import com.baur.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        return productService.getProduct(id);
    }

    @GetMapping("list")
    public List<Product> getProductList() {
        return productService.getProductList();
    }

    @PostMapping("/make_order")
    public void saveOrderToDB(@RequestBody List<Product> products) {
        productService.saveOrder(products);
    }

    @PostMapping("/add_product")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @DeleteMapping("delete_product/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
    }
}
