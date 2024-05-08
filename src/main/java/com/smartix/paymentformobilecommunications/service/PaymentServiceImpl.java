package com.smartix.paymentformobilecommunications.service;

import com.smartix.paymentformobilecommunications.dao.PaymentRepository;
import com.smartix.paymentformobilecommunications.dto.PaymentDTO;
import com.smartix.paymentformobilecommunications.dto.PaymentResponse;
import com.smartix.paymentformobilecommunications.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public PaymentResponse getPayments(int pageNo, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Payment> page = paymentRepository.findAll(pageable);

        List<Payment> paymentList = page.getContent();

        List<PaymentDTO> content = paymentList.stream().map(payment -> mapToPaymentDTO(payment)).collect(Collectors.toList());
        PaymentResponse response = new PaymentResponse();
        response.setContent(content);
        response.setPageNo(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;

    }

    @Override
    public PaymentDTO mapToPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setPhoneNumber(payment.getPhoneNumber());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setDate(payment.getDate());
        return paymentDTO;
    }

}
