package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.dto.PaymentDTO;
import com.smartix.paymentformobilecommunications.dto.PaymentResponse;
import com.smartix.paymentformobilecommunications.entity.Payment;

public interface PaymentService {

    public PaymentResponse getPayments(int pageNo, int pageSize);

    public PaymentDTO mapToPaymentDTO(Payment payment);

}
