package io.biza.deepthought.product.api.controller;

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
import io.biza.babelfish.cdr.exceptions.UnsupportedPayloadException;
import io.biza.babelfish.cdr.models.payloads.ErrorV1;
import io.biza.babelfish.cdr.models.responses.ResponseErrorListV1;
import io.biza.babelfish.cdr.support.BabelfishVersioner;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CDRController implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object inputBody, MethodParameter returnType,
      MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {

    Integer version;
    try {
      version = Integer.parseInt(request.getHeaders().getFirst("x-v"));
    } catch (NumberFormatException e) {
      LOG.error(String.format("Invalid version of %d requested, returning body unmodified",
          request.getHeaders().getFirst("x-v")));
      return inputBody;
    }

    Integer minimumVersion = null;

    try {
      minimumVersion = Integer.parseInt(request.getHeaders().getFirst("x-min-v"));
    } catch (NumberFormatException e) {
      // No-op, leave it null
    }


    try {
      Class<?> destinationType =
          BabelfishVersioner.getVersionedClass(inputBody.getClass(), version, minimumVersion);


      try {
        response.getHeaders().put("x-v",
            List.of(BabelfishVersioner.getVersion(destinationType).toString()));
      } catch (Exception e) {
        LOG.error(
            "Identified destination type but was then unable to obtain version again for type of {}",
            destinationType.getName());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
      /**
       * Add Interaction Id
       */
      response.getHeaders().put("x-fapi-interaction-id", request.getHeaders()
          .getOrDefault("x-fapi-interaction-id", List.of(UUID.randomUUID().toString())));

      return BabelfishVersioner.convert(inputBody, destinationType);
    } catch (Exception e) {
      e.printStackTrace();
      LOG.error(
          "Encountered payload conversion exception (and returning original object unmodified) while attempting to converter {} to a version between {} <= {}: {}",
          inputBody.getClass().getName(), minimumVersion, version, e.getMessage());
      return inputBody;
    }
  }

}
