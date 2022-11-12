package com.jobis.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  private static final int TIME_OUT_LIMIT = 15 * 1000;
  private static final int CONNECT_TIME_OUT = 5000;

  @Bean
  public RestTemplate restTemplate() {
    CloseableHttpClient httpClient = HttpClientBuilder.create()
        .setMaxConnTotal(50)
        .setMaxConnPerRoute(20)
        .build();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

    factory.setHttpClient(httpClient);
    factory.setConnectionRequestTimeout(CONNECT_TIME_OUT);
    factory.setConnectTimeout(CONNECT_TIME_OUT);
    factory.setReadTimeout(TIME_OUT_LIMIT);

    return new RestTemplate(new BufferingClientHttpRequestFactory(factory));
  }

}



