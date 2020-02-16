package io.biza.deepthought.admin.api.delegate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.data.payloads.dio.product.DioBankProduct;
import io.biza.deepthought.data.payloads.dio.product.DioProductBundle;

public interface ProductBundleAdminApiDelegate {
  default Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  default ResponseEntity<List<DioProductBundle>> listProductBundles(UUID brandId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductBundle> getProductBundle(UUID brandId, UUID productBundleId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductBundle> createProductBundle(UUID brandId,
      DioProductBundle productBundle) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductBundle(UUID brandId, UUID productBundleId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductBundle> updateProductBundle(UUID brandId, UUID productBundleId,
      DioProductBundle productBundle) throws ValidationListException {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<DioProductBundle> addProductToProductBundle(@NotNull @Valid UUID brandId,
      @NotNull @Valid UUID bundleId, @NotNull @Valid UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<Void> deleteProductFromProductBundle(@NotNull @Valid UUID brandId,
      @NotNull @Valid UUID bundleId, @NotNull @Valid UUID productId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

  default ResponseEntity<List<DioBankProduct>> listProductsForBundle(@NotNull @Valid UUID brandId,
      @NotNull @Valid UUID bundleId) {
    return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
  }

}
