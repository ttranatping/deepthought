package io.biza.deepthought.shared.persistence.mapper;

import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.cdr.CdrBankingProduct;
import io.biza.deepthought.shared.payloads.dio.product.DioProduct;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductData;
import ma.glasnost.orika.MapperFactory;

public class ProductBankingDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(BankProductData.class, DioProduct.class).fieldAToB("id", "id")
        .fieldAToB("product.name", "name").fieldAToB("product.description", "description")
        .field("", "cdrBanking").byDefault().register();

    orikaMapperFactory.classMap(BankProductData.class, CdrBankingProduct.class)
        .byDefault().register();

  }

}
