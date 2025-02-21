package com.twopro.deliveryapp.payment.service;

import com.twopro.deliveryapp.common.enumType.PaymentProvider;
import com.twopro.deliveryapp.common.enumType.PaymentStatus;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    private static final Map<PaymentProvider, String> PAYMENT_PROVIDERS = Map.of(
            PaymentProvider.TOSS, "www.tossbank.com",
            PaymentProvider.KAKAO, "www.kakaopay.com",
            PaymentProvider.NAVER, "www.naverpay.com",
            PaymentProvider.KB, "www.kbpay.com",
            PaymentProvider.IBK, "www.ibkpay.com",
            PaymentProvider.NH, "www.nhpay.com",
            PaymentProvider.WOORI, "www.wooripay.com"
    );

    @Override
    @Transactional
    public Payment createPayment(int totalPrice, PaymentProvider paymentProvider, Long userId) {
        URI uri = Optional.ofNullable(PAYMENT_PROVIDERS.get(paymentProvider))
                .map(url -> UriComponentsBuilder.fromUriString(url).encode().build().toUri())
                .orElseThrow(() -> new PaymentProviderNoSearchException("결제대행사가 올바르지 않습니다.", userId, paymentProvider));

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
                return paymentRepository.save(Payment.createPayment(totalPrice, PaymentStatus.SUCCESS, paymentProvider));
            } else {
                return paymentRepository.save(Payment.createPayment(totalPrice, PaymentStatus.FAIL, paymentProvider));
            }
        } catch (Exception e) {
            throw new PaymentnException("결제 API 요청 중 에러가 생겼습니다.");
        }
    }

    @Override
    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow();
    }
}