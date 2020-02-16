package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.product.DioProductCardArt;

public interface ProductCardArtAdminApiDelegate {
  
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioProductCardArt>> listProductCardArts(UUID brandId, UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductCardArt> getProductCardArt(UUID brandId, UUID productId, UUID cardArtId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductCardArt> createProductCardArt(UUID brandId, UUID productId,
      DioProductCardArt cardArt) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductCardArt(UUID brandId, UUID productId, UUID cardArtId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductCardArt> updateProductCardArt(UUID brandId, UUID productId, UUID cardArtId,
      DioProductCardArt cardArt) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
