package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.dao.PaymentRepository;
import com.smartix.paymentformobilecommunications.dto.PaymentDTO;
import com.smartix.paymentformobilecommunications.dto.PaymentResponse;
import com.smartix.paymentformobilecommunications.entity.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    public void getPayments_returnsPaymentResponse() {
        Page<Payment> page = Mockito.mock(Page.class);
        when(paymentRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        PaymentResponse savePayment = paymentService.getPayments(1, 10);

        assertNotNull(savePayment);
    }

    @Test
    public void mapToPaymentDTO_returnsPaymentDTO() {
        Payment payment = new Payment();
        payment.setPhoneNumber("+7 999 999 99 99");
        payment.setAmount(200);
        payment.setId(1);

        PaymentDTO expected = new PaymentDTO();
        expected.setPhoneNumber(payment.getPhoneNumber());
        expected.setAmount(payment.getAmount());
        expected.setId(payment.getId());
        expected.setDate(payment.getDate());

        PaymentDTO result = paymentService.mapToPaymentDTO(payment);

        assertNotNull(result);
        assertEquals(expected, result);
    }

}