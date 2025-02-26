package webdev.e_commerce.common.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatePaymentCommand {
    @TargetAggregateIdentifier
    private String id;
    private String orderId;
    private String buyerId;
    private Long amount;
}
