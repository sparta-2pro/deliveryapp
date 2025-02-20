package com.twopro.deliveryapp.payment.service;

import com.twopro.deliveryapp.order.excepiton.PaymentProviderNoSearchException;
import com.twopro.deliveryapp.order.excepiton.PaymentnException;
import com.twopro.deliveryapp.payment.entity.Payment;
import com.twopro.deliveryapp.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    private static final Map<String, String> PAYMENT_PROVIDERS = Map.of(
            "TOSS", "www.tossbank.com",
            "KAKAO", "www.kakaopay.com"
    );

    @Transactional
    public Payment createPayment(int totalPrice, String paymentProvider) {
        URI uri = Optional.ofNullable(PAYMENT_PROVIDERS.get(paymentProvider))
                .map(url -> UriComponentsBuilder.fromUriString(url).encode().build().toUri())
                .orElseThrow(() -> new PaymentProviderNoSearchException("결제대행사가 올바르지 않습니다."));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.putAll(Map.of(
                "Price", List.of(String.valueOf(totalPrice)),
                "client_id", List.of("8c6fa8b17bc7a36008b1b6726c8d277b")
        ));

        try {
            //ResponseEntity<String> response = restTemplate.exchange(RequestEntity.post(uri).headers(headers).body(body), String.class);
//            if (response.getStatusCode().is2xxSuccessful()) { // 실제 api를 쓸 수 없어서 무조건 true로 성공하게 주석처리
            if(true){
                return paymentRepository.save(Payment.createPayment(totalPrice, "Y", paymentProvider));
            } else {
                return paymentRepository.save(Payment.createPayment(totalPrice, "N", paymentProvider));
            }
        } catch (Exception e) {
            throw new PaymentnException("결제 API 요청 중 에러가 생겼습니다.");
        }
    }
}