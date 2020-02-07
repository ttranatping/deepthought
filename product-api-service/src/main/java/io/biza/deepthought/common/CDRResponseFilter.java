package io.biza.deepthought.common;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import io.biza.babelfish.cdr.support.BabelfishVersioner;

@Component
public class CDRResponseFilter {

  public void convert() {
    /**
    try {
      responseEntity.getHeaders().put("x-v",
          List.of(BabelfishVersioner.getVersion(destinationType).toString()));
    } catch (Exception e) {
      LOG.error(
          "Identified destination type but was then unable to obtain version again for type of {}",
          destinationType.getName());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
*/
    /**
     * Add Interaction Id
     */
  /**  responseEntity.getHeaders().put("x-fapi-interaction-id", request.getHeaders()
        .getOrDefault("x-fapi-interaction-id", List.of(UUID.randomUUID().toString())));
*/

  }
}
