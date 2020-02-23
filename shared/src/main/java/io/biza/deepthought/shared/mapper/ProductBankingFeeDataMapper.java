package io.biza.deepthought.shared.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeeV1;
import io.biza.deepthought.data.payloads.dio.product.DioProductFee;
import io.biza.deepthought.data.persistence.model.product.banking.BankProductFeeData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
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
