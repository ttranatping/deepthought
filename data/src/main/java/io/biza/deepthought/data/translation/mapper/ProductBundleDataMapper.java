package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.v1.model.banking.BankingProductBundle;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payload.DioProductBundle;
import io.biza.deepthought.data.persistence.model.ProductBundleData;
import ma.glasnost.orika.MapperFactory;

public class ProductBundleDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductBundleData.class, DioProductBundle.class).exclude("brand")
        .byDefault().fieldAToB("id", "id").register();
    // TODO: productIds conversion
    orikaMapperFactory.classMap(ProductBundleData.class, BankingProductBundle.class)
        .exclude("brand").byDefault().register();
  }

}
