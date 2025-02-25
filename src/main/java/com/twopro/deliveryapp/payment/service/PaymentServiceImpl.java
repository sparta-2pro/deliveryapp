package com.twopro.deliveryapp.payment.service;

import com.twopro.deliveryapp.common.enumType.PaymentProvider;
import com.twopro.deliveryapp.common.enumType.PaymentStatus;
import com.twopro.deliveryapp.order.entity.Order;
import com.twopro.deliveryapp.order.excepiton.PaymentProviderNoSearchException;
import com.twopro.deliveryapp.order.excepiton.PaymentnException;
import com.twopro.deliveryapp.order.service.OrderService;
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
        URI uri = getPaymentProviderUri(paymentProvider, userId);

        Map<String, String> body = Map.of(
                "Price", String.valueOf(totalPrice),
                "client_id", "8c6fa8b17bc7a36008b1b6726c8d277b"
        );

        boolean isSuccess = callPaymentApi(uri, body);
        return paymentRepository.save(Payment.createPayment(
                totalPrice,
                isSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAIL,
                paymentProvider
        ));
    }

    @Override
    @Transactional
    public void orderCancel(UUID orderId, Order order) {
        Payment findPayment = order.getPayment();

        URI uri = getPaymentProviderUri(findPayment.getPaymentProvider(), null);

        Map<String, String> body = Map.of(
                "Price", String.valueOf(findPayment.getTotalPrice()),
                "client_id", "8c6fa8b17bc7a36008b1b6726c8d277b"
        );

        boolean isSuccess = callPaymentApi(uri, body);
        if (isSuccess) {
            findPayment.updateStatus(PaymentStatus.CANCELLED);
        } else {
            throw new PaymentnException("결제 취소 요청이 실패했습니다.");
        }
    }

    @Override
    public Payment findById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow();
    }

    private boolean callPaymentApi(URI uri, Map<String, String> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.setAll(body);

        try {
            // ResponseEntity<String> response = restTemplate.exchange(
            //     RequestEntity.post(uri).headers(headers).body(requestBody), String.class);
            // return response.getStatusCode().is2xxSuccessful(); // 실제 API 연동 시 사용

            return true; // 실제 API를 호출하지 않으므로 항상 성공 처리
        } catch (Exception e) {
            throw new PaymentnException("결제 API 요청 중 에러가 발생했습니다.");
        }
    }

    private URI getPaymentProviderUri(PaymentProvider provider, Long userId) {
        return Optional.ofNullable(PAYMENT_PROVIDERS.get(provider))
                .map(url -> UriComponentsBuilder.fromUriString(url).encode().build().toUri())
                .orElseThrow(() -> new PaymentProviderNoSearchException("결제대행사가 올바르지 않습니다.", userId, provider));
    }
}
