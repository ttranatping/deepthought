package io.biza.deepthought.data.translation.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductLendingRateV1;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTierV1;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTierApplicabilityV1;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioProductRateLending;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingTierApplicabilityData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateLendingTierData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class ProductCdrBankingRateLendingDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {

    orikaMapperFactory
        .classMap(ProductCdrBankingRateLendingData.class, BankingProductLendingRateV1.class)
        .exclude("tiers").byDefault()
        .customize(new CustomMapper<ProductCdrBankingRateLendingData, BankingProductLendingRateV1>() {
          @Override
          public void mapAtoB(ProductCdrBankingRateLendingData from, BankingProductLendingRateV1 to,
              MappingContext context) {

            List<BankingProductRateTierV1> tierList =
                new ArrayList<BankingProductRateTierV1>();

            if (from.tiers() != null) {
              for (ProductCdrBankingRateLendingTierData tierData : from.tiers()) {
                BankingProductRateTierV1 rateTier = new BankingProductRateTierV1();
                rateTier.maximumValue(tierData.maximumValue());
                rateTier.minimumValue(tierData.minimumValue());
                rateTier.name(tierData.name());
                rateTier.rateApplicationMethod(tierData.rateApplicationMethod());
                rateTier.unitOfMeasure(tierData.unitOfMeasure());
                if (tierData.applicabilityConditions() != null) {
                  rateTier.applicabilityConditions(new BankingProductRateTierApplicabilityV1()
                      .additionalInfo(tierData.applicabilityConditions().additionalInfo())
                      .additionalInfoUri(tierData.applicabilityConditions().additionalInfoUri()));
                }

                tierList.add(rateTier);
              }
            }

            to.tiers(tierList);
          }
        }).register();

    orikaMapperFactory.classMap(ProductCdrBankingRateLendingData.class, DioProductRateLending.class)
        .fieldAToB("id", "id").field("schemeType", "schemeType")
        .field("lendingRateType", "cdrBanking.lendingRateType").field("rate", "cdrBanking.rate")
        .field("comparisonRate", "cdrBanking.comparisonRate")
        .field("applicationFrequency", "cdrBanking.applicationFrequency")
        .field("calculationFrequency", "cdrBanking.calculationFrequency")
        .field("interestPaymentDue", "cdrBanking.interestPaymentDue")
        .field("additionalValue", "cdrBanking.additionalValue")
        .field("additionalInfo", "cdrBanking.additionalInfo")
        .field("additionalInfoUri", "cdrBanking.additionalInfoUri")
        .customize(new CustomMapper<ProductCdrBankingRateLendingData, DioProductRateLending>() {
          @Override
          public void mapAtoB(ProductCdrBankingRateLendingData from, DioProductRateLending to,
              MappingContext context) {

            List<BankingProductRateTierV1> tierList =
                new ArrayList<BankingProductRateTierV1>();

            if (from.tiers() != null) {

              for (ProductCdrBankingRateLendingTierData tierData : from.tiers()) {
                BankingProductRateTierV1 rateTier = new BankingProductRateTierV1();
                rateTier.maximumValue(tierData.maximumValue());
                rateTier.minimumValue(tierData.minimumValue());
                rateTier.name(tierData.name());
                rateTier.rateApplicationMethod(tierData.rateApplicationMethod());
                rateTier.unitOfMeasure(tierData.unitOfMeasure());
                if (tierData.applicabilityConditions() != null) {
                  rateTier.applicabilityConditions(new BankingProductRateTierApplicabilityV1()
                      .additionalInfo(tierData.applicabilityConditions().additionalInfo())
                      .additionalInfoUri(tierData.applicabilityConditions().additionalInfoUri()));
                }

                tierList.add(rateTier);
              }
            }

            to.cdrBanking().tiers(tierList);
          }

          @Override
          public void mapBtoA(DioProductRateLending from, ProductCdrBankingRateLendingData to,
              MappingContext context) {

            Set<ProductCdrBankingRateLendingTierData> tierList =
                new HashSet<ProductCdrBankingRateLendingTierData>();

            if (from.cdrBanking().tiers() != null) {
              for (BankingProductRateTierV1 tierData : from
                  .cdrBanking().tiers()) {
                ProductCdrBankingRateLendingTierData rateTier =
                    new ProductCdrBankingRateLendingTierData();
                rateTier.maximumValue(tierData.maximumValue());
                rateTier.minimumValue(tierData.minimumValue());
                rateTier.name(tierData.name());
                rateTier.rateApplicationMethod(tierData.rateApplicationMethod());
                rateTier.unitOfMeasure(tierData.unitOfMeasure());
                if (tierData.applicabilityConditions() != null) {
                  rateTier.applicabilityConditions(
                      new ProductCdrBankingRateLendingTierApplicabilityData()
                          .additionalInfo(tierData.applicabilityConditions().additionalInfo())
                          .additionalInfoUri(
                              tierData.applicabilityConditions().additionalInfoUri()));
                }
                tierList.add(rateTier);
              }
            }
            to.tiers(tierList);
          }
        }).register();
  }

}
