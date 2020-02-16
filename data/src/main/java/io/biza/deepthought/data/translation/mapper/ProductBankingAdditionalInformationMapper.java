package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductAdditionalInformationV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.persistence.model.bank.product.ProductBankingAdditionalInformationData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingAdditionalInformationMapper
    implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(ProductBankingAdditionalInformationData.class,
            BankingProductAdditionalInformationV1.class)
        .field("feesPricingUri", "feesAndPricingUri").byDefault().register();
  }

}
