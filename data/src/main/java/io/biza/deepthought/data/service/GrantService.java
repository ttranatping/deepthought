package io.biza.deepthought.data.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData_;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.persistence.model.grant.GrantData;
import io.biza.deepthought.data.persistence.model.grant.GrantData_;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.persistence.model.product.ProductData_;
import io.biza.deepthought.data.repository.GrantRepository;
import io.biza.deepthought.data.repository.ProductRepository;
import io.biza.deepthought.data.specification.GrantSpecifications;
import io.biza.deepthought.data.specification.ProductBankingSpecifications;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GrantService {

  @Autowired
  GrantRepository grantRepository;

  public List<GrantData> getActiveGrantsBySubject(String subject) {
    LOG.debug("Retrieving a list of active grants for {}", subject);
    Specification<GrantData> filterSpecifications = GrantSpecifications.subject(subject)
        .and(GrantSpecifications.expiryBefore(OffsetDateTime.now()));
    return grantRepository.findAll(filterSpecifications);
  }  
}
