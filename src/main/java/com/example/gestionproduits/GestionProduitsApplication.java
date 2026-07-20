package com.example.gestionproduits;

import com.example.gestionproduits.entities.Product;
import com.example.gestionproduits.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionProduitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionProduitsApplication.class, args);
    }

    // Jeu de donnees de demo, cree au demarrage (utile avec H2 en memoire)
    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder().name("Ordinateur portable").price(6500).quantity(12).build());
            productRepository.save(Product.builder().name("Imprimante laser").price(950).quantity(30).build());
            productRepository.save(Product.builder().name("Ecran 27 pouces").price(1800).quantity(20).build());
            productRepository.save(Product.builder().name("Clavier mecanique").price(320).quantity(50).build());
            productRepository.save(Product.builder().name("Souris sans fil").price(150).quantity(80).build());
            productRepository.findAll().forEach(p -> System.out.println(p.getName() + " - " + p.getPrice() + " DH"));
        };
    }
}
