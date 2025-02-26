package webdev.e_commerce.product_catalog_service.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import webdev.e_commerce.product_catalog_service.dto.requestdto.CategoryProductRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductRequestDTO;
import webdev.e_commerce.product_catalog_service.repository.ProductRepository;
import webdev.e_commerce.product_catalog_service.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class DataInitializer {

    private final ProductService productService;
    private final ProductRepository repository;
    @Bean
    CommandLineRunner init(ProductService productService) {
        return args -> {
            // Only initialize if the database is empty
            if (repository.count() == 0) {
                List<ProductRequestDTO> productsToCreate = Arrays.asList(
                        new ProductRequestDTO(
                                "iPhone 14 Pro",
                                "Apple's latest flagship smartphone with A16 Bionic chip",
                                "Apple Store",
                                "Apple",
                                100,
                                new BigDecimal("999.99"),
                                new CategoryProductRequestDTO("Smartphones")
                        ),
                        new ProductRequestDTO(
                                "Samsung Galaxy S23 Ultra",
                                "Premium Android smartphone with S Pen support",
                                "Samsung Electronics",
                                "Samsung",
                                75,
                                new BigDecimal("1199.99"),
                                new CategoryProductRequestDTO("Smartphones")
                        ),
                        new ProductRequestDTO(
                                "MacBook Pro 16",
                                "16-inch laptop with M2 Pro chip",
                                "Apple Store",
                                "Apple",
                                50,
                                new BigDecimal("2499.99"),
                                new CategoryProductRequestDTO("Laptops")
                        ),
                        new ProductRequestDTO(
                                "Sony WH-1000XM4",
                                "Wireless noise-canceling headphones",
                                "Sony Electronics",
                                "Sony",
                                200,
                                new BigDecimal("349.99"),
                                new CategoryProductRequestDTO("Headphones")
                        ),
                        new ProductRequestDTO(
                                "Dell XPS 15",
                                "15-inch premium laptop with Intel i9",
                                "Dell",
                                "Dell",
                                30,
                                new BigDecimal("1899.99"),
                                new CategoryProductRequestDTO("Laptops")
                        ),
                        new ProductRequestDTO(
                                "iPad Pro 12.9",
                                "12.9-inch tablet with M2 chip",
                                "Apple Store",
                                "Apple",
                                85,
                                new BigDecimal("1099.99"),
                                new CategoryProductRequestDTO("Tablets")
                        ),
                        new ProductRequestDTO(
                                "Bose QuietComfort 45",
                                "Premium wireless noise-canceling headphones",
                                "Bose",
                                "Bose",
                                150,
                                new BigDecimal("329.99"),
                                new CategoryProductRequestDTO("Headphones")
                        )
                );

                productService.saveAll(productsToCreate);

                System.out.println("Database initialized with " + productsToCreate.size() + " products");
            } else {
                System.out.println("Database already contains products, skipping initialization");
            }
        };
    }
}