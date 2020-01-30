package io.biza.deepthought.product.api.delegate;

import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductV2ById;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductV2List;
import io.biza.deepthought.product.api.requests.RequestListProducts;

public interface ProductApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<ResponseBankingProductV2List> listProducts(@Valid RequestListProducts requestList) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<ResponseBankingProductV2ById> getProductDetail(UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }
}
