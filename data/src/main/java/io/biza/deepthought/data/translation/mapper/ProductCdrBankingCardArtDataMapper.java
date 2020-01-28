package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.v2.model.banking.BankingProductCardArt;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payload.DioProductCardArt;
import ma.glasnost.orika.MapperFactory;

public class ProductCdrBankingCardArtDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory
        .classMap(io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingCardArtData.class,
            DioProductCardArt.class)
        .fieldAToB("id", "id").field("", "cdrBanking").byDefault().register();

    orikaMapperFactory
        .classMap(io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingCardArtData.class,
            BankingProductCardArt.class)
        .byDefault().register();

  }

}
