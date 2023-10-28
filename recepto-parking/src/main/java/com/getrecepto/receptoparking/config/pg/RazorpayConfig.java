package com.getrecepto.receptoparking.config.pg;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RazorpayConfig {
    private final String API_KEY;
    private final String API_SECRET;

    @Autowired
    public RazorpayConfig(@Value("${spring.pg.razorpay.apiKey:#{null}}") String apiKey,
                          @Value("${spring.pg.razorpay.apiSecret:#{null}}") String apiSecret) {
        this.API_KEY = apiKey;
        this.API_SECRET = apiSecret;
    }

    @Bean("RazorpayClient")
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        if (StringUtils.isEmpty(this.API_KEY) || StringUtils.isEmpty(this.API_SECRET)) {
            log.error("apikey and apiSecret are nor properly configured,, returning null");
            return null;
        }
        return new RazorpayClient("key_id", "key_secret");
    }
}
