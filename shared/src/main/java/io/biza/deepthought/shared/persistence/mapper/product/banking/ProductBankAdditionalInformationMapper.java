package io.biza.deepthought.shared.persistence.mapper.product.banking;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductAdditionalInformationV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.persistence.model.product.banking.ProductBankAdditionalInformationData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankAdditionalInformationMapper
    implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductBankAdditionalInformationData.class,
            BankingProductAdditionalInformationV1.class)
        .field("feesPricingUri", "feesAndPricingUri").byDefault().register();
  }

}
