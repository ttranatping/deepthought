package io.biza.deepthought.common.api.delegate;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerDetailV1;
import io.biza.babelfish.cdr.models.responses.ResponseCommonCustomerV1;
import io.biza.deepthought.shared.exception.InvalidSubjectException;

public interface CommonCustomerApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseCommonCustomerV1> getCustomer() throws InvalidSubjectException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
  
  default ResponseEntity<ResponseCommonCustomerDetailV1> getCustomerDetail() throws InvalidSubjectException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
