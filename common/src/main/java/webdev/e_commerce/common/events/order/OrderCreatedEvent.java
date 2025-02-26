package webdev.e_commerce.common.events.order;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import webdev.e_commerce.common.Status;
import webdev.e_commerce.common.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreatedEvent {
    String id;
    String buyerId;
    List<OrderItemDTO> orderedItems;
    Status status;
    String address;
    BigDecimal totalAmount;
}
