package webdev.e_commerce.product_catalog_service.service;

import org.springframework.data.domain.Page;
import webdev.e_commerce.common.dto.OrderItemDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductUpdateRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.responsedto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO save(ProductRequestDTO productRequestDto);

    void saveAll(List<ProductRequestDTO> productRequestDtos);

    ProductResponseDTO findById(String id);

    Page<ProductResponseDTO> findAll(int page, int size);

    Page<ProductResponseDTO> findAllSorted(int page, int size, String sortField);

    ProductResponseDTO update(ProductUpdateRequestDTO productRequestDTO);

    void updateInventory(List<OrderItemDTO> items);

}