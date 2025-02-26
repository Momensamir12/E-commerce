package webdev.e_commerce.product_catalog_service.service;

import webdev.e_commerce.product_catalog_service.dto.responsedto.ProductResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductFilterService {

    List<ProductResponseDTO> filterByName(String name, int page, int size);

    List<ProductResponseDTO> filterByPriceRange(BigDecimal min, BigDecimal max, int page, int size);

    List<ProductResponseDTO> filterByCategory(String category, int page, int size);
}
