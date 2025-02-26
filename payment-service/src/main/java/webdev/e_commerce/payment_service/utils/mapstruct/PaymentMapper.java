package webdev.e_commerce.payment_service.utils.mapstruct;

import org.mapstruct.Mapper;
import webdev.e_commerce.payment_service.dto.response.PaymentResponseDTO;
import webdev.e_commerce.payment_service.dto.response.ProcessPaymentResponseDTO;
import webdev.e_commerce.payment_service.model.Payment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment processpaymentResponseDTOToPayment(ProcessPaymentResponseDTO response);

    List<PaymentResponseDTO> paymentsToPaymentsResponseDTO(List<Payment> payments);
}
