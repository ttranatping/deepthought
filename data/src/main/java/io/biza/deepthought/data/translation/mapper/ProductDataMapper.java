package io.biza.deepthought.data.translation.mapper;

import io.biza.babelfish.cdr.v2.model.banking.BankingProduct;
import io.biza.babelfish.cdr.v2.model.banking.BankingProductDetail;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payload.DioProduct;
import io.biza.deepthought.data.persistence.model.ProductData;
import ma.glasnost.orika.MapperFactory;

public class ProductDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(ProductData.class, DioProduct.class).fieldAToB("id", "id")
        .byDefault().register();

    orikaMapperFactory.classMap(ProductData.class, BankingProduct.class)
        .fieldAToB("id", "productId").fieldAToB("name", "name")
        .fieldAToB("description", "description")
        .fieldAToB("cdrBanking.effectiveFrom", "effectiveFrom")
        .fieldAToB("cdrBanking.effectiveTo", "effectiveTo")
        .fieldAToB("cdrBanking.lastUpdated", "lastUpdated")
        .fieldAToB("cdrBanking.productCategory", "productCategory").fieldAToB("brand.name", "brand")
        .fieldAToB("brand.displayName", "brandName")
        .fieldAToB("cdrBanking.applicationUri", "applicationUri")
        .fieldAToB("cdrBanking.isTailored", "tailored")
        .fieldAToB("cdrBanking.additionalInformation", "additionalInformation").byDefault()
        .register();

    orikaMapperFactory.classMap(ProductData.class, BankingProductDetail.class)
        .fieldAToB("id", "productId").fieldAToB("name", "name")
        .fieldAToB("description", "description")
        .fieldAToB("cdrBanking.effectiveFrom", "effectiveFrom")
        .fieldAToB("cdrBanking.effectiveTo", "effectiveTo")
        .fieldAToB("cdrBanking.lastUpdated", "lastUpdated")
        .fieldAToB("cdrBanking.productCategory", "productCategory").fieldAToB("brand.name", "brand")
        .fieldAToB("brand.displayName", "brandName")
        .fieldAToB("cdrBanking.applicationUri", "applicationUri")
        .fieldAToB("cdrBanking.isTailored", "tailored")
        .fieldAToB("cdrBanking.additionalInformation", "additionalInformation")
        .fieldAToB("cdrBanking.feature", "features")
        .fieldAToB("cdrBanking.constraint", "constraints").fieldAToB("cdrBanking.fee", "fees")
        .fieldAToB("cdrBanking.depositRate", "depositRates")
        .fieldAToB("cdrBanking.lendingRate", "lendingRates").byDefault().register();
  }
}
