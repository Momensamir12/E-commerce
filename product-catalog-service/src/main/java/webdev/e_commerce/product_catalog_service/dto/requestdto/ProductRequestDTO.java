package webdev.e_commerce.product_catalog_service.dto.requestdto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO(


        String name,
        @NotBlank(message = "Please enter product description")
        String description,
        @NotBlank(message = "Please enter product seller")
        String seller,
        @NotBlank(message = "Please enter product brand")
        String brand,
        @NotNull
        Integer inStockQuantity,
        @NotNull(message = "Please enter product price")
        BigDecimal price,
        @Valid
        CategoryProductRequestDTO category

) {
}