package webdev.e_commerce.payment_service.service;


import webdev.e_commerce.common.events.payment.ProcessingPaymentEvent;
import webdev.e_commerce.payment_service.dto.request.ProcessPaymentRequestDTO;
import webdev.e_commerce.payment_service.dto.response.PaymentResponseDTO;
import webdev.e_commerce.payment_service.dto.response.ProcessPaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    ProcessPaymentResponseDTO process(ProcessPaymentRequestDTO request);

    List<PaymentResponseDTO> findAll(String buyerId);

    List<PaymentResponseDTO> findAll();

    void init(ProcessingPaymentEvent event);
}
