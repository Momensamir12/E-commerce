package webdev.e_commerce.payment_service.service;

import webdev.e_commerce.payment_service.dto.request.ProcessPaymentRequestDTO;
import webdev.e_commerce.payment_service.dto.response.ProcessPaymentResponseDTO;

public interface PaymentProviderService {
    ProcessPaymentResponseDTO process(ProcessPaymentRequestDTO request);
}
