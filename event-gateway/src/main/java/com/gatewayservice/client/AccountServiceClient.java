package com.gatewayservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gatewayservice.config.FeignConfig;
import com.gatewayservice.dto.EventRequest;

@FeignClient(
        name = "account-service",
        url = "${account.service.url}",
        configuration = FeignConfig.class
)
public interface AccountServiceClient {

    @PostMapping("/accounts/transactions")
    Object processTransaction(@RequestBody EventRequest request);

}