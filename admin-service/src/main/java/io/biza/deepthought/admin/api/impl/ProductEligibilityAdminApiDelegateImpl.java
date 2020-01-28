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
import io.biza.deepthought.admin.api.delegate.ProductEligibilityAdminApiDelegate;
import io.biza.deepthought.admin.exceptions.ValidationListException;
import io.biza.deepthought.admin.support.DeepThoughtValidator;
import io.biza.deepthought.data.enumerations.DioExceptionType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payload.DioProductEligibility;
import io.biza.deepthought.data.persistence.model.ProductData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingEligibilityData;
import io.biza.deepthought.data.repository.ProductEligibilityRepository;
import io.biza.deepthought.data.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Controller
@Validated
@Slf4j
public class ProductEligibilityAdminApiDelegateImpl implements ProductEligibilityAdminApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  ProductEligibilityRepository eligibilityRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private Validator validator;
  
  @Override
  public ResponseEntity<List<DioProductEligibility>> listProductEligibilitys(UUID brandId,
      UUID productId) {

    List<ProductCdrBankingEligibilityData> eligibilityList = eligibilityRepository
        .findAllByProduct_Product_Brand_IdAndProduct_Product_Id(brandId, productId);
    LOG.debug("Listing eligibilitys and have database result of {}", eligibilityList);
    return ResponseEntity.ok(mapper.mapAsList(eligibilityList, DioProductEligibility.class));
  }

  @Override
  public ResponseEntity<DioProductEligibility> getProductEligibility(UUID brandId, UUID productId,
      UUID id) {
    Optional<ProductCdrBankingEligibilityData> data = eligibilityRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (data.isPresent()) {
      LOG.debug("Get Product Eligibility for brand {} and product {} returning: {}", brandId,
          productId, data.get());
      return ResponseEntity.ok(mapper.map(data.get(), DioProductEligibility.class));
    } else {
      LOG.warn("Get product eligibility for brand {} and product {} not found", brandId, productId,
          data.get());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductEligibility> createProductEligibility(UUID brandId,
      UUID productId, DioProductEligibility createData) throws ValidationListException {

    DeepThoughtValidator.validate(validator, createData);
    
    Optional<ProductData> product = productRepository.findByIdAndBrandId(productId, brandId);

    if (!product.isPresent()) {
      throw ValidationListException.builder().type(DioExceptionType.INVALID_BRAND_AND_PRODUCT).explanation(Labels.ERROR_INVALID_BRAND_AND_PRODUCT).build();
    }

    ProductCdrBankingEligibilityData data =
        mapper.map(createData, ProductCdrBankingEligibilityData.class);

    LOG.debug("Attempting to save: {}", data);

    if (!product.get().schemeType().equals(createData.schemeType())) {
      throw ValidationListException.builder().type(DioExceptionType.UNSUPPORTED_PRODUCT_SCHEME_TYPE).explanation(Labels.ERROR_UNSUPPORTED_PRODUCT_SCHEME_TYPE).build();
    }

    if (product.get().schemeType().equals(DioSchemeType.CDR_BANKING)) {
      data.product(product.get().cdrBanking());
    }

    data = eligibilityRepository.save(data);
    LOG.debug("Create Product Eligibility for brand {} and product {} returning: {}", brandId,
        productId, data);
    return getProductEligibility(brandId, productId, data.id());
  }

  @Override
  public ResponseEntity<Void> deleteProductEligibility(UUID brandId, UUID productId, UUID id) {
    Optional<ProductCdrBankingEligibilityData> optionalData = eligibilityRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (optionalData.isPresent()) {
      LOG.debug("Deleting product eligibility with brand: {} productId: {} id: {}", brandId,
          productId, id);
      eligibilityRepository.delete(optionalData.get());
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<DioProductEligibility> updateProductEligibility(UUID brandId,
      UUID productId, UUID id, DioProductEligibility updateData) throws ValidationListException {
    
    DeepThoughtValidator.validate(validator, updateData);

    Optional<ProductCdrBankingEligibilityData> optionalData = eligibilityRepository
        .findByIdAndProduct_Product_Brand_IdAndProduct_Product_Id(id, brandId, productId);

    if (optionalData.isPresent()) {
      ProductCdrBankingEligibilityData data = optionalData.get();
      mapper.map(updateData, data);
      eligibilityRepository.save(data);
      LOG.debug("Updated product eligibility for brand: {} productId: {} id: {} with data of {}",
          brandId, productId, id, data);
      return getProductEligibility(brandId, productId, eligibilityRepository.save(data).id());
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
