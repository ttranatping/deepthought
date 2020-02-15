package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeeV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.product.DioProductFee;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeeData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingFeeDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductBankingFeeData.class, DioProductFee.class)
        .field("", "cdrBanking").fieldAToB("id", "id").byDefault().register();

    orikaMapperFactory.classMap(ProductBankingFeeData.class, BankingProductFeeV1.class).byDefault()
        .register();
  }

}
