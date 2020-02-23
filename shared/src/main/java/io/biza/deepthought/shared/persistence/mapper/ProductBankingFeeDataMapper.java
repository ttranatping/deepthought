package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeeV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductFee;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductFeeData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeeDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankProductFeeData.class, DioProductFee.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();

    orikaMapperFactory.classMap(BankProductFeeData.class, BankingProductFeeV1.class).byDefault()
        .register();
  }

}
