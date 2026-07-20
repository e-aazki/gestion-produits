package com.example.gestionproduits.web;

import com.example.gestionproduits.entities.Product;
import com.example.gestionproduits.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    // ---- Liste + recherche + pagination ----
    @GetMapping("/index")
    public String index(Model model,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        Page<Product> pageProducts = productRepository.findByNameContainsIgnoreCase(
                keyword, PageRequest.of(page, size));

        model.addAttribute("listProducts", pageProducts.getContent());
        model.addAttribute("pages", new int[pageProducts.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    // ---- Suppression ----
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id,
                          @RequestParam(name = "page", defaultValue = "0") int page,
                          @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        productRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    // ---- Formulaire d'ajout ----
    @GetMapping("/formProduct")
    public String formProduct(Model model) {
        model.addAttribute("product", new Product());
        return "formProduct";
    }

    // ---- Formulaire d'edition ----
    @GetMapping("/editProduct")
    public String editProduct(Model model, @RequestParam(name = "id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));
        model.addAttribute("product", product);
        return "formProduct";
    }

    // ---- Enregistrement (ajout ou mise a jour) avec validation ----
    @PostMapping("/save")
    public String save(@Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "formProduct";
        }
        productRepository.save(product);
        return "redirect:/index";
    }
}
