package com.example.gestionproduits.repositories;

import com.example.gestionproduits.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Recherche paginee (utilisee par la vue liste + barre de recherche)
    Page<Product> findByNameContainsIgnoreCase(String keyword, Pageable pageable);

    // Recherche simple (utilisee dans les tests DAO)
    List<Product> findByNameContainsIgnoreCase(String keyword);

    List<Product> findByPriceGreaterThan(double price);
}
