package webdev.e_commerce.common.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import webdev.e_commerce.common.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class CompleteOrderCommand {
    @TargetAggregateIdentifier
    String id;
    Status status = Status.COMPLETED;
}
