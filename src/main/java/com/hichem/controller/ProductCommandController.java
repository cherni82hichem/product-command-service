package com.hichem.controller;

import com.hichem.dto.ProductEvent;
import com.hichem.entity.Product;
import com.hichem.service.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

    @Autowired
    private ProductCommandService commandService;

    @PostMapping
    public Product createProduct(@RequestBody ProductEvent product){
        return commandService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody ProductEvent product){
        return commandService.updateProduct(id,product);
    }
}
