package webdev.e_commerce.product_catalog_service.commands;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import webdev.e_commerce.common.commands.UpdateInventoryCommand;
import webdev.e_commerce.common.dto.OrderItemDTO;
import webdev.e_commerce.product_catalog_service.service.ProductService;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class ProductsCommandHandler {
    private final ProductService productService;

    @CommandHandler
    public void on(UpdateInventoryCommand cmd) {
        List<OrderItemDTO> orderedProducts = cmd.getOrderedItems();
        productService.updateInventory(orderedProducts);
    }
}
