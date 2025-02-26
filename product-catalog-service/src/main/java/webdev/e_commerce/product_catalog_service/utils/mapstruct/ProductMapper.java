package webdev.e_commerce.product_catalog_service.utils.mapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductUpdateRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.responsedto.ProductResponseDTO;
import webdev.e_commerce.product_catalog_service.model.Product;

import java.util.List;

@Mapper(componentModel = "spring")

public interface ProductMapper {


    @Mapping(source = "id", target = "auditing.id")
    @Mapping(source = "createdTime", target = "auditing.createdTime")
    @Mapping(source = "lastModifiedTime", target = "auditing.lastModifiedTime")
    ProductResponseDTO productToProductResponseDTO(Product product);

    Product productRequestDtoToProduct(ProductRequestDTO productRequestDto);

    @Mapping(source = "id", target = "auditing.id")
    @Mapping(source = "createdTime", target = "auditing.createdTime")
    @Mapping(source = "lastModifiedTime", target = "auditing.lastModifiedTime")
    List<ProductResponseDTO> productsToProductResponeDTO(List<Product> products);

    Product productUpdateRequestDTOToProduct(ProductUpdateRequestDTO productUpdateRequestDTO);

}