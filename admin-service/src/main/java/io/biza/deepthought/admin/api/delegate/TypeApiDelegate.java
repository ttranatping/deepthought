package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.data.enumerations.FormFieldType;
import io.biza.deepthought.data.payloads.ResponseGetTypes;

public interface TypeApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseGetTypes> getTypes(List<FormFieldType> labelTypes) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
