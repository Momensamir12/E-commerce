package webdev.e_commerce.product_catalog_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webdev.e_commerce.common.APIResponse;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductRequestDTO;
import webdev.e_commerce.product_catalog_service.dto.requestdto.ProductUpdateRequestDTO;
import webdev.e_commerce.product_catalog_service.service.ProductService;
import webdev.e_commerce.product_catalog_service.utils.api.APIMessages;

import java.util.List;

import static webdev.e_commerce.product_catalog_service.utils.api.APIHelperUtil.createUnifiedResponse;
import static webdev.e_commerce.product_catalog_service.utils.api.APIURI.adminURI;

@RestController
@RequestMapping(adminURI)
@AllArgsConstructor
@Tag(name = "Product catalog service Admin Controller", description = "APIs related to admin operations in product catalog service")
public class ProductAdminController {
    private final ProductService productService;

    @Operation(summary = "Add a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product details")
    })
    @PostMapping
    public ResponseEntity<APIResponse> add(@Valid @RequestBody ProductRequestDTO productRequestDto) {
        return createUnifiedResponse(productService.save(productRequestDto), HttpStatus.CREATED, APIMessages.Product_CREATED_SUCCESSFULLY);
    }

    @Operation(summary = "Add multiple new products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Products created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product details in the request")
    })
    @PostMapping("/bulk")
    public ResponseEntity<APIResponse> add(@Valid @RequestBody List<ProductRequestDTO> productRequestDTOList) {
        productService.saveAll(productRequestDTOList);
        return createUnifiedResponse("Products created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product details"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/update")
    public ResponseEntity<APIResponse> update(@RequestBody @Valid ProductUpdateRequestDTO product) {
        return createUnifiedResponse(productService.update(product), HttpStatus.OK);
    }
}
