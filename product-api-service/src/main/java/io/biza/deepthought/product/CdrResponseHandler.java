package io.biza.deepthought.product;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import io.biza.babelfish.cdr.abstracts.responses.CDRResponsePaginatedV1;
import io.biza.babelfish.cdr.exceptions.PayloadConversionException;
import io.biza.babelfish.cdr.exceptions.UnsupportedPayloadException;
import io.biza.babelfish.cdr.models.payloads.ErrorV1;
import io.biza.babelfish.cdr.models.responses.ResponseErrorListV1;
import io.biza.babelfish.cdr.support.BabelfishVersioner;
import io.biza.deepthought.common.support.CDRVersioner;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CdrResponseHandler implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object inputBody, MethodParameter returnType,
      MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
        
    /**
     * If not supported do nothing
     */
    if(!BabelfishVersioner.isSupported(inputBody.getClass())) {
      return inputBody;
    }
    
    /**
     * Add Interaction Id
     */
    response.getHeaders().put("x-fapi-interaction-id", request.getHeaders()
        .getOrDefault("x-fapi-interaction-id", List.of(UUID.randomUUID().toString())));

    /**
     * Check if we should be converting
     */
    try {
      if(CDRVersioner.needsConversion(inputBody.getClass())) {
        try {
          inputBody = CDRVersioner.convert(inputBody);
        } catch (PayloadConversionException e) {
          LOG.error("Payload conversion error encountered: {}", e.getMessage());
          response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
          return null;
        }
      }
    } catch (UnsupportedPayloadException ex) {
      LOG.error("Attempted to perform conversion and got exception: {}", ex);
      response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
      return null;
    }
    
    /**
     * Add x-v header
     */
    try {
      response.getHeaders().put("x-v",
          List.of(BabelfishVersioner.getVersion(inputBody.getClass()).toString()));
    } catch (Exception e) {
      LOG.error(
          "Identified destination type but was then unable to obtain version again for type of {}",
          inputBody.getClass().getName());
    }
    
    return inputBody;
  }


}