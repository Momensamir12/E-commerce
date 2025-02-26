package webdev.e_commerce.product_catalog_service.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {

    @NotEmpty
    private String name;
}