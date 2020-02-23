package io.biza.deepthought.shared.persistence.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductLendingRateV1;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTierV1;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.product.DioProductRateLending;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductRateLendingData;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductRateLendingTierApplicabilityData;
import io.biza.deepthought.shared.persistence.model.product.banking.BankProductRateLendingTierData;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTierApplicabilityV1;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class ProductBankingRateLendingDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {

    orikaMapperFactory
        .classMap(BankProductRateLendingData.class, BankingProductLendingRateV1.class)
        .exclude("tiers").byDefault()
        .customize(new CustomMapper<BankProductRateLendingData, BankingProductLendingRateV1>() {
          @Override
          public void mapAtoB(BankProductRateLendingData from, BankingProductLendingRateV1 to,
              MappingContext context) {

            List<BankingProductRateTierV1> tierList =
                new ArrayList<BankingProductRateTierV1>();

            if (from.tiers() != null) {
              for (BankProductRateLendingTierData tierData : from.tiers()) {
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

    orikaMapperFactory.classMap(BankProductRateLendingData.class, DioProductRateLending.class)
        .fieldAToB("id", "id").field("schemeType", "schemeType")
        .field("lendingRateType", "cdrBanking.lendingRateType").field("rate", "cdrBanking.rate")
        .field("comparisonRate", "cdrBanking.comparisonRate")
        .field("applicationFrequency", "cdrBanking.applicationFrequency")
        .field("calculationFrequency", "cdrBanking.calculationFrequency")
        .field("interestPaymentDue", "cdrBanking.interestPaymentDue")
        .field("additionalValue", "cdrBanking.additionalValue")
        .field("additionalInfo", "cdrBanking.additionalInfo")
        .field("additionalInfoUri", "cdrBanking.additionalInfoUri")
        .customize(new CustomMapper<BankProductRateLendingData, DioProductRateLending>() {
          @Override
          public void mapAtoB(BankProductRateLendingData from, DioProductRateLending to,
              MappingContext context) {

            List<BankingProductRateTierV1> tierList =
                new ArrayList<BankingProductRateTierV1>();

            if (from.tiers() != null) {

              for (BankProductRateLendingTierData tierData : from.tiers()) {
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
          public void mapBtoA(DioProductRateLending from, BankProductRateLendingData to,
              MappingContext context) {

            Set<BankProductRateLendingTierData> tierList =
                new HashSet<BankProductRateLendingTierData>();

            if (from.cdrBanking().tiers() != null) {
              for (BankingProductRateTierV1 tierData : from
                  .cdrBanking().tiers()) {
                BankProductRateLendingTierData rateTier =
                    new BankProductRateLendingTierData();
                rateTier.maximumValue(tierData.maximumValue());
                rateTier.minimumValue(tierData.minimumValue());
                rateTier.name(tierData.name());
                rateTier.rateApplicationMethod(tierData.rateApplicationMethod());
                rateTier.unitOfMeasure(tierData.unitOfMeasure());
                if (tierData.applicabilityConditions() != null) {
                  rateTier.applicabilityConditions(
                      new BankProductRateLendingTierApplicabilityData()
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
