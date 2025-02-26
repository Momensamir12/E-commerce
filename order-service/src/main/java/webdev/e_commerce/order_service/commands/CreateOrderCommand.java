package webdev.e_commerce.order_service.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import webdev.e_commerce.common.Status;
import webdev.e_commerce.order_service.dto.request.OrderItemRequestDTO;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    String id;
    String buyerId;
    List<OrderItemRequestDTO> orderedItems;
    Status status;
    String address;
    BigDecimal totalAmount;
}
