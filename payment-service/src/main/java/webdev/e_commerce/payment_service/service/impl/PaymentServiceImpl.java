package webdev.e_commerce.payment_service.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webdev.e_commerce.common.events.payment.ProcessingPaymentEvent;
import webdev.e_commerce.payment_service.commands.PaymentCommandHandler;
import webdev.e_commerce.payment_service.dto.request.ProcessPaymentRequestDTO;
import webdev.e_commerce.payment_service.dto.response.PaymentResponseDTO;
import webdev.e_commerce.payment_service.dto.response.ProcessPaymentResponseDTO;
import webdev.e_commerce.payment_service.exception.exceptions.PaymentNotFoundException;
import webdev.e_commerce.payment_service.model.Payment;
import webdev.e_commerce.payment_service.model.enums.PaymentStatus;
import webdev.e_commerce.payment_service.repository.PaymentRepository;
import webdev.e_commerce.payment_service.service.PaymentProviderService;
import webdev.e_commerce.payment_service.service.PaymentService;
import webdev.e_commerce.payment_service.utils.CurrentAuthContext;
import webdev.e_commerce.payment_service.utils.mapstruct.PaymentMapper;

import java.util.List;
import java.util.UUID;

import static webdev.e_commerce.payment_service.exception.PaymentErrorMessages.PAYMENT_NOT_FOUND_ERROR;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentProviderService paymentProviderService;
    private final PaymentCommandHandler paymentCommandHandler;
    @Override
    @Transactional
    public ProcessPaymentResponseDTO process(ProcessPaymentRequestDTO request) {
        Payment payment = paymentRepository.findByOrderId(request.orderId());
        if (payment == null) {
            throw new PaymentNotFoundException(PAYMENT_NOT_FOUND_ERROR);
        }
        payment.setProvider(request.provider());
        ProcessPaymentResponseDTO response = paymentProviderService.process(request);
        payment.setStatus(response.paymentStatus());
        payment.setCreatedBy(CurrentAuthContext.getUserEmail());
        payment.setCurrency(response.currency());
        Payment savedPayment = paymentRepository.save(payment);
        paymentCommandHandler.paymentStatusHandler(savedPayment);
        return response;
    }

    @Override
    @Transactional
    public void init(ProcessingPaymentEvent event) {
        log.info("Created payment for order id {}", event.getOrderId());
        Payment payment = new Payment();
        payment.setId(UUID.fromString(event.getPaymentId()));
        payment.setOrderId(event.getOrderId());
        payment.setBuyerId(event.getBuyerId());
        payment.setCreatedBy("");
        payment.setAmount(event.getAmount());
        payment.setStatus(PaymentStatus.CREATED);
        paymentRepository.save(payment);
    }

    @Override
    public List<PaymentResponseDTO> findAll(String buyerId) {
        if (buyerId == null || buyerId.isEmpty()) {
            throw new IllegalStateException("Buyer id is invalid");
        }
        List<Payment> payments = paymentRepository.findAllByBuyerId(buyerId);
        if (payments == null || payments.isEmpty()) {
            throw new EntityNotFoundException("No payments found for buyer ID: " + buyerId);
        }
        return paymentMapper.paymentsToPaymentsResponseDTO(paymentRepository.findAllByBuyerId(buyerId));
    }

    @Override
    public List<PaymentResponseDTO> findAll() {
        String buyerId = CurrentAuthContext.getUserID();
        if (buyerId == null || buyerId.isEmpty()) {
            throw new IllegalStateException("Buyer id is invalid or user isn't registered");
        }
        List<Payment> payments = paymentRepository.findAllByBuyerId(buyerId);
        if (payments == null || payments.isEmpty()) {
            throw new EntityNotFoundException("No payments found for buyer ID: " + buyerId);
        }
        return paymentMapper.paymentsToPaymentsResponseDTO(paymentRepository.findAllByBuyerId(CurrentAuthContext.getUserID()));
    }
}
