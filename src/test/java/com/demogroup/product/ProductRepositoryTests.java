package com.demogroup.product;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demogroup.product.dao.ProductRepository;
import com.demogroup.product.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTests {

  @Autowired
  ProductRepository productRepository;
  
  @Test
  public void createTest () {
    Product product = new Product();
    product.setName("TestItem");
    productRepository.save(product);
    Assert.assertNotNull(productRepository.findOneByName("TestItem"));
    productRepository.delete(product);
    Assert.assertNull(productRepository.findOneByName("TestItem"));
  }
  
  @Test
  public void updateTest () {
    Product product = new Product();
    product.setName("TestItem");
    productRepository.save(product);
    Assert.assertNotNull(productRepository.findOneByName("TestItem"));
    product.setName("TestItemUpdated");
    productRepository.save(product);
    Assert.assertNotNull(productRepository.findOneByName("TestItemUpdated"));
    productRepository.delete(product);
    Assert.assertNull(productRepository.findOneByName("TestItemUpdated"));
  }
  
}
