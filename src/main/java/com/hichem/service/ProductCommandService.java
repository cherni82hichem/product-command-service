package com.hichem.service;

import com.hichem.dto.ProductEvent;
import com.hichem.entity.Product;
import com.hichem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public Product createProduct(ProductEvent productEvent){
        Product product =  productRepo.save(productEvent.getProduct());
        ProductEvent event = new ProductEvent("createdProduct",product);
        kafkaTemplate.send("product-event-topic",event);
        return  product;
    }

    public Product updateProduct(Long id, ProductEvent productEvent){
        Product existantProduct = productRepo.findById(id).get();
        existantProduct.setDescription(productEvent.getProduct().getDescription());
        existantProduct.setPrice(productEvent.getProduct().getPrice());
        existantProduct.setName(productEvent.getProduct().getName());
        Product productUpdated = productRepo.save(existantProduct);
        ProductEvent event = new ProductEvent("updatedProduct",productUpdated);
        kafkaTemplate.send("product-event-topic",event);
        return productUpdated;
    }
}
