package webdev.e_commerce.product_catalog_service.dto.requestdto;

import jakarta.validation.constraints.NotBlank;

public record CategoryProductRequestDTO(
        @NotBlank
        String name
) {
}
