package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductAdditionalInformation;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingAdditionalInformationData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingAdditionalInformationMapper
    implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductCdrBankingAdditionalInformationData.class,
            BankingProductAdditionalInformation.class)
        .field("feesPricingUri", "feesAndPricingUri").byDefault().register();
  }

}
