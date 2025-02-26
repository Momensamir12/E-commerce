package webdev.e_commerce.product_catalog_service.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Document(collection = "products")
@Accessors(chain = true)
@NoArgsConstructor
@Data
public class Product extends BaseEntity {
    @Indexed
    private String name;
    @NotBlank(message = "Please enter product's description")
    private String description;
    @NotBlank(message = "Please enter product's seller")
    private String seller;
    @NotBlank(message = "Please enter product's brand")
    private String brand;
    @NotNull
    private Integer inStockQuantity;
    private Integer soldQuantity;
    @NotNull(message = "Please enter product's price")
    @Field(targetType = DECIMAL128)
    private BigDecimal price;
    @NotBlank(message = "Please enter product's category")
    @Field(targetType = FieldType.STRING)
    @Valid
    private Category category;
}