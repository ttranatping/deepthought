package io.biza.deepthought.shared.persistence.mapper;

import java.util.ArrayList;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductBundleV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductBundle;
import io.biza.deepthought.shared.persistence.model.product.ProductBundleData;
import io.biza.deepthought.shared.persistence.model.product.ProductData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class ProductBundleDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductBundleData.class, DioProductBundle.class).exclude("brand")
        .byDefault().fieldAToB("id", "id").register();
    orikaMapperFactory.classMap(ProductBundleData.class, BankingProductBundleV1.class)
        .exclude("brand").customize(new CustomMapper<ProductBundleData, BankingProductBundleV1>() {
          @Override
          public void mapAtoB(ProductBundleData from, BankingProductBundleV1 to,
              MappingContext context) {

            if (from.products() != null) {
              to.productIds(new ArrayList<String>());
              for (ProductData product : from.products()) {
                to.productIds().add(product.id().toString());
              }
            }

          }
        }).byDefault().register();
  }
}
