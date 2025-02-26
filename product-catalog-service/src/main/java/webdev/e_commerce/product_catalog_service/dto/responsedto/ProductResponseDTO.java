package webdev.e_commerce.product_catalog_service.dto.responsedto;


import java.math.BigDecimal;

public record ProductResponseDTO(

        BaseResponseDTO auditing,
        String name,
        String description,
        String seller,
        String brand,
        Integer inStockQuantity,
        BigDecimal price,
        CategoryResponseDTO category
) {
}
