package io.biza.deepthought.product.api.impl;

import java.util.Optional;
import java.util.UUID;
import javax.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDetailV2;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductV2;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductV2ById;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductV2List;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingProductV2ListData;
import io.biza.deepthought.common.CDRContainerAttributes;
import io.biza.deepthought.data.persistence.model.ProductData;
import io.biza.deepthought.data.repository.BrandRepository;
import io.biza.deepthought.data.repository.ProductRepository;
import io.biza.deepthought.data.specification.ProductSpecifications;
import io.biza.deepthought.product.DeepThoughtMapper;
import io.biza.deepthought.product.api.delegate.ProductApiDelegate;
import io.biza.deepthought.product.api.requests.RequestListProducts;
import lombok.extern.slf4j.Slf4j;

@Validated
@Controller
@Slf4j
public class ProductApiDelegateImpl implements ProductApiDelegate {

  @Autowired
  private DeepThoughtMapper mapper;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private BrandRepository brandRepository;

  @Autowired
  private Validator validator;

  @Override
  public ResponseEntity<ResponseBankingProductV2List> listProducts(
      RequestListProducts requestList) {
    LOG.debug("Retrieving a list of products with input request of {}", requestList);
    
    Specification<ProductData> filterSpecifications = Specification.where(null);
    
    if(requestList.effective() != null) {
      filterSpecifications = filterSpecifications.and(ProductSpecifications.effective(requestList.effective()));
    }
    
    if(requestList.updatedSince() != null) {
      filterSpecifications = filterSpecifications.and(ProductSpecifications.updatedSince(requestList.updatedSince()));
    }
    
    if(requestList.productCategory() != null) {
      filterSpecifications = filterSpecifications.and(ProductSpecifications.productCategory(requestList.productCategory()));
    }
    
    if(StringUtils.isNotBlank(requestList.brand())) {
      filterSpecifications = filterSpecifications.and(ProductSpecifications.brand(requestList.brand()));
    }
    
    /**
     * Paginated Result
     */
    Page<ProductData> productList =
        productRepository.findAll(filterSpecifications, PageRequest.of(requestList.page() - 1, requestList.pageSize()));

    /**
     * Build response components
     */
    ResponseBankingProductV2List listResponse = new ResponseBankingProductV2List();
    listResponse.meta(CDRContainerAttributes.toMetaPaginated(productList));
    listResponse.links(CDRContainerAttributes.toLinksPaginated(productList));
    listResponse.data(ResponseBankingProductV2ListData.builder()
        .products(mapper.mapAsList(productList.getContent(), BankingProductV2.class)).build());
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingProductV2ById> getProductDetail(UUID productId) {
    LOG.debug("Retrieving product with identifier of {}", productId);
    Optional<ProductData> productResult = productRepository.findById(productId);
    if (productResult.isPresent()) {
      ResponseBankingProductV2ById productResponse = new ResponseBankingProductV2ById();
      productResponse.meta(CDRContainerAttributes.toMeta());
      productResponse.links(CDRContainerAttributes.toLinks());
      productResponse.data(mapper.map(productResult.get(), BankingProductDetailV2.class));
      LOG.debug("Product Response returned is: {}", productResponse);
      return ResponseEntity.ok(productResponse);
    } else {
      LOG.warn("Invalid Product Identifier of {} requested, returning 400 Error", productId);
      return ResponseEntity.badRequest().build();
    }
  }

}
