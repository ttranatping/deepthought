package io.biza.deepthought.discovery.api.delegate;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseCommonDiscoveryOutagesListV1;
import io.biza.babelfish.cdr.models.responses.ResponseCommonDiscoveryStatusV1;

public interface CommonDiscoveryApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }
  
  default ResponseEntity<ResponseCommonDiscoveryStatusV1> getStatus() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseCommonDiscoveryOutagesListV1> getOutages() {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
