package io.biza.deepthought.shared.persistence.mapper;

import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductCardArtV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductCardArt;
import ma.glasnost.orika.MapperFactory;

public class ProductBankCardArtDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(io.biza.deepthought.shared.persistence.model.product.banking.ProductBankCardArtData.class,
            DioProductCardArt.class)
        .fieldAToB("id", "id").field("", "cdrBanking").byDefault().register();

    orikaMapperFactory
        .classMap(io.biza.deepthought.shared.persistence.model.product.banking.ProductBankCardArtData.class,
            BankingProductCardArtV1.class)
        .byDefault().register();

  }

}
