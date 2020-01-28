package io.biza.deepthought.admin.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.deepthought.admin.DeepThoughtMapper;
import io.biza.deepthought.admin.Labels;
import io.biza.deepthought.admin.api.delegate.ProductBundleAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.payload.DioProductBundle;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.ProductBundleData;
import io.biza.deepthought.data.repository.BrandRepository;
import io.biza.deepthought.data.repository.ProductBundleRepository;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class ProductBundleAdminApiDelegateImpl implements ProductBundleAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  private ProductBundleRepository bundleRepository;

  @Autowired
  private BrandRepository brandRepository;
  
  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<List<DioProductBundle>> listProductBundles(UUID brandId) {
    List<ProductBundleData> productData = bundleRepository.findAllByBrandId(brandId);
    LOG.debug("Listing product bundles and have database result of {}", productData);
    return ResponseEntity.ok(mapper.mapAsList(productData, DioProductBundle.class));
  }

  @Override
  public ResponseEntity<DioProductBundle> getProductBundle(UUID brandId, UUID productId) {
    Optional<ProductBundleData> data = bundleRepository.findByIdAndBrandId(productId, brandId);

    if (data.isPresent()) {
      LOG.debug("Retrieving product bundle with brand of {}, identifier of {} and content of {}",
          brandId, productId, data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioProductBundle.class));
    } else {
      LOG.warn("Unable to locate product bundle with brand of {} and identifier of {}", brandId,
          productId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductBundle> createProductBundle(UUID brandId,
      DioProductBundle createData) throws ValidationListException {
    Optional<BrandData> brand = brandRepository.findById(brandId);

    if (!brand.isPresent()) {
      LOG.warn("Attempted to create product bundle for brand that doesn't exist of brandId {}",
          brandId);
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND)
          .explanation(Labels.ERROR_INVALID_BRAND).build();
    }
    
    DeepThoughtValidator.validate(validator, createData);

    ProductBundleData data = mapper.map(createData, ProductBundleData.class);
    data.brand(brand.get());
    LOG.debug("Created product bundle on brand {} with content of {}", brandId, data);
    return getProductBundle(brandId, bundleRepository.save(data).id());
  }

  @Override
  public ResponseEntity<Void> deleteProductBundle(UUID brandId, UUID productId) {
    Optional<ProductBundleData> optionalData =
        bundleRepository.findByIdAndBrandId(productId, brandId);

    if (optionalData.isPresent()) {
      LOG.debug("Deleting product bundle for brand {} and product {}", brandId, productId);
      bundleRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductBundle> updateProductBundle(UUID brandId, UUID productId,
      DioProductBundle updateData) throws ValidationListException {
    
    DeepThoughtValidator.validate(validator, updateData);
    
    Optional<ProductBundleData> optionalData =
        bundleRepository.findByIdAndBrandId(productId, brandId);

    if (optionalData.isPresent()) {
      ProductBundleData data = optionalData.get();
      mapper.map(updateData, data);
      data.id(productId);
      bundleRepository.save(data);
      LOG.debug("Updated product bundle with brand {} and product {} containing data of {}",
          brandId, productId, data);
      return getProductBundle(brandId, data.id());
    } else {
      LOG.warn("Attempted to update product bundle that doesn't exist with brand {} and product {}",
          brandId, productId);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}