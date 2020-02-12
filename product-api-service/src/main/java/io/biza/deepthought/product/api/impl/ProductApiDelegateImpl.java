package io.biza.deepthought.product.api.impl;

import java.util.Optional;
import java.util.UUID;
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
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductByIdV2;
import io.biza.babelfish.cdr.models.responses.ResponseBankingProductListV2;
import io.biza.babelfish.cdr.models.responses.container.ResponseBankingProductListDataV2;
import io.biza.deepthought.common.support.CDRContainerAttributes;
import io.biza.deepthought.data.component.DeepThoughtMapper;
import io.biza.deepthought.data.persistence.model.ProductData;
import io.biza.deepthought.data.repository.ProductRepository;
import io.biza.deepthought.data.specification.ProductSpecifications;
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

  @Override
  public ResponseEntity<ResponseBankingProductListV2> listProducts(
      RequestListProducts requestList) {
    LOG.debug("Retrieving a list of products with input request of {}", requestList);

    Specification<ProductData> filterSpecifications = Specification.where(null);

    if (requestList.effective() != null) {
      filterSpecifications =
          filterSpecifications.and(ProductSpecifications.effective(requestList.effective()));
    }

    if (requestList.updatedSince() != null) {
      filterSpecifications =
          filterSpecifications.and(ProductSpecifications.updatedSince(requestList.updatedSince()));
    }

    if (requestList.productCategory() != null) {
      filterSpecifications = filterSpecifications
          .and(ProductSpecifications.productCategory(requestList.productCategory()));
    }

    if (StringUtils.isNotBlank(requestList.brand())) {
      filterSpecifications =
          filterSpecifications.and(ProductSpecifications.brand(requestList.brand()));
    }

    /**
     * Paginated Result
     */
    Page<ProductData> productList = productRepository.findAll(filterSpecifications,
        PageRequest.of(requestList.page() - 1, requestList.pageSize()));

    /**
     * Build response components
     */
    ResponseBankingProductListV2 listResponse = ResponseBankingProductListV2.builder()
        .meta(CDRContainerAttributes.toMetaPaginated(productList))
        .links(CDRContainerAttributes.toLinksPaginated(productList))
        .data(ResponseBankingProductListDataV2.builder()
            .products(mapper.mapAsList(productList.getContent(), BankingProductV2.class)).build())
        .build();
    LOG.debug("List response came back with: {}", listResponse);
    return ResponseEntity.ok(listResponse);
  }

  @Override
  public ResponseEntity<ResponseBankingProductByIdV2> getProductDetail(UUID productId) {
    LOG.debug("Retrieving product with identifier of {}", productId);
    Optional<ProductData> productResult = productRepository.findById(productId);
    if (productResult.isPresent()) {
      ResponseBankingProductByIdV2 productResponse = new ResponseBankingProductByIdV2();
      productResponse.meta(CDRContainerAttributes.toMeta());
      productResponse.links(CDRContainerAttributes.toLinks());
      productResponse.data(mapper.map(productResult.get(), BankingProductDetailV2.class));
      
      return ResponseEntity.ok(productResponse);
      
    } else {
      LOG.warn("Invalid Product Identifier of {} requested, returning 400 Error", productId);
      return ResponseEntity.badRequest().build();
    }
  }

}
