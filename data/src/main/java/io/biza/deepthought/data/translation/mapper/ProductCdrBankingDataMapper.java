package io.biza.deepthought.data.translation.mapper;

import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payload.DioProduct;
import io.biza.deepthought.data.payload.cdr.CdrBankingProduct;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingData;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductCdrBankingData.class, DioProduct.class).fieldAToB("id", "id")
        .fieldAToB("product.name", "name").fieldAToB("product.description", "description")
        .field("", "cdrBanking").byDefault().register();

    orikaMapperFactory.classMap(ProductCdrBankingData.class, CdrBankingProduct.class)
        .field("isTailored", "tailored").byDefault().register();

    /**
     * orikaMapperFactory.classMap(ProductCdrBankingData.class, BankingProduct.class).field("id",
     * "productId") .field("isTailored", "tailored").byDefault().register();
     * 
     * orikaMapperFactory.classMap(ProductCdrBankingData.class,
     * BankingProductDetail.class).field("id", "productId") .field("isTailored",
     * "tailored").byDefault().register();
     */
  }

}
