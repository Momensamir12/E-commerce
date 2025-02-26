package webdev.e_commerce.product_catalog_service.service.serviceimpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import webdev.e_commerce.common.dto.OrderItemDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductUpdateRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.responsedto.ProductResponseDTO;
import webdev.e_commerce.product_catalog_service.exception.exceptions.ProductNotFoundException;
import webdev.e_commerce.product_catalog_service.model.Product;
import webdev.e_commerce.product_catalog_service.repository.ProductRepository;
import webdev.e_commerce.product_catalog_service.service.ProductService;
import webdev.e_commerce.product_catalog_service.utils.mapstruct.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static webdev.e_commerce.product_catalog_service.exception.ErrorMessages.PRODUCT_NOT_FOUND_ERROR;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    @Override
    public ProductResponseDTO save(ProductRequestDTO productRequestDto) {
        Product product = productMapper.productRequestDtoToProduct(productRequestDto);
        Product persisted = productRepository.save(product);
        return productMapper.productToProductResponseDTO(persisted);
    }

    @Override
    public void saveAll(List<ProductRequestDTO> productRequestDTOList) {
        List<Product> products = productRequestDTOList.stream()
                .map(productMapper::productRequestDtoToProduct)
                .toList();
        productRepository.saveAll(products);
    }

    @Override
    public ProductResponseDTO findById(String id) {
        Product p = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND_ERROR));
        return productMapper.productToProductResponseDTO(p);
    }

    @Override
    public Page<ProductResponseDTO> findAll(int page, int size) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::productToProductResponseDTO);
    }

    @Override
    public Page<ProductResponseDTO> findAllSorted(int page, int size, String sortField) {
        if (page < 0 || size < 0) {
            page = 0;
            size = 10;
        }
        if (sortField == null || sortField.trim().isEmpty()) {
            throw new IllegalArgumentException("Sort field cannot be null");
        }
        Sort sort = Sort.by(sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::productToProductResponseDTO);
    }


    @Override
    public ProductResponseDTO update(ProductUpdateRequestDTO productUpdateRequestDTO) {
        Product p = productRepository.findById(productUpdateRequestDTO.id()).orElseThrow(
                () -> new ProductNotFoundException(PRODUCT_NOT_FOUND_ERROR));
        Product updatedProduct = productMapper.productUpdateRequestDTOToProduct(productUpdateRequestDTO);
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.productToProductResponseDTO(savedProduct);
    }

    @Override
    public void updateInventory(List<OrderItemDTO> items) {
        List<String> productIds = items.stream()
                .map(OrderItemDTO::productId)
                .collect(Collectors.toList());

        // Retrieve products from the database
        List<Product> products = productRepository.findAllById(productIds);

        Map<String, OrderItemDTO> itemMap = items.stream()
                .collect(Collectors.toMap(OrderItemDTO::productId, item -> item));

        // Update the quantity of each product
        for (Product product : products) {
            OrderItemDTO orderItem = itemMap.get(product.getId());
            if (orderItem != null) {
                int newQuantity = product.getInStockQuantity() - orderItem.quantity();
                if (newQuantity < 0) {
                    throw new IllegalArgumentException("Insufficient inventory for product ID: " + product.getId());
                }
                System.out.println(orderItem + "Updated");
                product.setInStockQuantity(newQuantity);
            }
        }
        productRepository.saveAll(products);
    }
}