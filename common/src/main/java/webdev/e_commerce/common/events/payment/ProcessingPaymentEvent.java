package webdev.e_commerce.common.events.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ProcessingPaymentEvent {
    private String paymentId;
    private String orderId;
    private String buyerId;
    private Long amount;
}
