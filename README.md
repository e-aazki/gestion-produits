<<<<<<< HEAD
# gestion-produits
Application Web JEE basée sur Spring, Spring Data JPA, Hibernate, Tymeleaf et Spring Security qui permet de gérer des produits
=======
# Gestion des produits — Spring Boot / Spring Data JPA / Thymeleaf / Spring Security

Application JEE construite en suivant la progression classique :

1. **Projet Spring Boot** — voir `pom.xml` : Web, Spring Data JPA, H2, MySQL, Thymeleaf,
   Lombok, Spring Security, Validation, + `thymeleaf-layout-dialect` et
   `thymeleaf-extras-springsecurity6` pour le template maître et les tags `sec:*`.
2. **Entité JPA `Product`** — `entities/Product.java` (id, name, price, quantity)
   avec annotations de validation (`@NotEmpty`, `@Size`, `@Min`, `@DecimalMin`).
3. **`ProductRepository`** — `repositories/ProductRepository.java`, interface Spring
   Data JPA avec recherche paginée (`findByNameContainsIgnoreCase`) et recherche simple.
4. **Test de la couche DAO** — `src/test/.../ProductRepositoryTest.java` (`@DataJpaTest`).
5. **Désactivation de la sécurité par défaut** — voir le bloc commenté en haut de
   `security/SecurityConfig.java` (à activer pendant le développement des vues).
6. **Contrôleur MVC + vues Thymeleaf** — `web/ProductController.java` :
   - `/index` : liste des produits (pagination + recherche)
   - `/delete` : suppression d'un produit
   - `template.html` : template maître Bootstrap (layout-dialect)
   - `formProduct.html` : ajout/édition avec validation (`th:errors`)
7. **Sécurisation réelle** — `security/SecurityConfig.java` : deux utilisateurs en
   mémoire (`admin/1234` rôle ADMIN, `user/1234` rôle USER), page de login
   personnalisée (`login.html`), suppression/ajout/édition réservés à ADMIN.
8. **Fonctionnalités supplémentaires** :
   - Recherche de produits par nom (barre de recherche + requête dérivée)
   - Édition/mise à jour d'un produit (`/editProduct`)
   - Pagination de la liste
   - Console H2 activée sur `/h2-console` pour inspecter la base en dev

## Lancer l'application

Prérequis : Java 17+, Maven (ou le wrapper `./mvnw` si vous en générez un), accès internet
pour télécharger les dépendances Maven la première fois.

```bash
mvn spring-boot:run
```

Par défaut le profil actif est `h2` (base en mémoire, jeu de données de démo créé au
démarrage). L'application est disponible sur http://localhost:8080/index

Comptes de test :
- `admin` / `1234` → rôle ADMIN (accès complet : ajout, édition, suppression)
- `user` / `1234` → rôle USER (lecture seule)

Console H2 : http://localhost:8080/h2-console
(JDBC URL : `jdbc:h2:mem:products-db`, user `sa`, pas de mot de passe)

## Basculer vers MySQL

1. Créez une base MySQL (ou laissez `createDatabaseIfNotExist=true` la créer).
2. Ajustez `application-mysql.properties` (url / user / password).
3. Démarrez avec :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Lancer les tests

```bash
mvn test
```
>>>>>>> 5b6030e (Initial commit)
