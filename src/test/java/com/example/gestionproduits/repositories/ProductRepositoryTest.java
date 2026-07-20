package com.example.gestionproduits.repositories;

import com.example.gestionproduits.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // charge uniquement la couche JPA + une base H2 en memoire pour le test
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindProduct() {
        Product product = Product.builder()
                .name("Telephone")
                .price(3500)
                .quantity(10)
                .build();

        Product saved = productRepository.save(product);

        assertThat(saved.getId()).isNotNull();
        assertThat(productRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void testFindByNameContainsIgnoreCase() {
        productRepository.save(Product.builder().name("Ordinateur HP").price(5000).quantity(5).build());
        productRepository.save(Product.builder().name("Ordinateur Dell").price(5200).quantity(3).build());
        productRepository.save(Product.builder().name("Imprimante Epson").price(800).quantity(15).build());

        List<Product> results = productRepository.findByNameContainsIgnoreCase("ordinateur");

        assertThat(results).hasSize(2);
    }

    @Test
    void testFindByPriceGreaterThan() {
        productRepository.save(Product.builder().name("Produit cher").price(9000).quantity(1).build());
        productRepository.save(Product.builder().name("Produit pas cher").price(50).quantity(100).build());

        List<Product> results = productRepository.findByPriceGreaterThan(1000);

        assertThat(results).extracting(Product::getName).containsExactly("Produit cher");
    }
}
