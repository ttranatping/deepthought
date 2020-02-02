package io.biza.deepthought.data.translation.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductLendingRate;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTier;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTierApplicability;
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
        .classMap(ProductCdrBankingRateLendingData.class, BankingProductLendingRate.class)
        .exclude("tiers").byDefault()
        .customize(new CustomMapper<ProductCdrBankingRateLendingData, BankingProductLendingRate>() {
          @Override
          public void mapAtoB(ProductCdrBankingRateLendingData from, BankingProductLendingRate to,
              MappingContext context) {

            List<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>> tierList =
                new ArrayList<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>>();

            for (ProductCdrBankingRateLendingTierData tierData : from.tiers()) {
              BankingProductRateTier rateTier = new BankingProductRateTier();
              rateTier.setMaximumValue(tierData.maximumValue());
              rateTier.setMinimumValue(tierData.minimumValue());
              rateTier.setName(tierData.name());
              rateTier.setRateApplicationMethod(tierData.rateApplicationMethod());
              rateTier.setUnitOfMeasure(tierData.unitOfMeasure());
              if (tierData.applicabilityConditions() != null) {
                rateTier.applicabilityConditions(new BankingProductRateTierApplicability()
                    .additionalInfo(tierData.applicabilityConditions().additionalInfo())
                    .additionalInfoUri(tierData.applicabilityConditions().additionalInfoUri()));
              }

              tierList.add(rateTier);
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

            List<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>> tierList =
                new ArrayList<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>>();

            for (ProductCdrBankingRateLendingTierData tierData : from.tiers()) {
              BankingProductRateTier rateTier = new BankingProductRateTier();
              rateTier.setMaximumValue(tierData.maximumValue());
              rateTier.setMinimumValue(tierData.minimumValue());
              rateTier.setName(tierData.name());
              rateTier.setRateApplicationMethod(tierData.rateApplicationMethod());
              rateTier.setUnitOfMeasure(tierData.unitOfMeasure());
              if (tierData.applicabilityConditions() != null) {
                rateTier.applicabilityConditions(new BankingProductRateTierApplicability()
                    .additionalInfo(tierData.applicabilityConditions().additionalInfo())
                    .additionalInfoUri(tierData.applicabilityConditions().additionalInfoUri()));
              }

              tierList.add(rateTier);
            }

            to.cdrBanking().tiers(tierList);
          }

          @Override
          public void mapBtoA(DioProductRateLending from, ProductCdrBankingRateLendingData to,
              MappingContext context) {

            Set<ProductCdrBankingRateLendingTierData> tierList =
                new HashSet<ProductCdrBankingRateLendingTierData>();

            for (io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?> tierData : from
                .cdrBanking().tiers()) {
              ProductCdrBankingRateLendingTierData rateTier =
                  new ProductCdrBankingRateLendingTierData();
              rateTier.maximumValue(tierData.maximumValue());
              rateTier.minimumValue(tierData.minimumValue());
              rateTier.name(tierData.name());
              rateTier.rateApplicationMethod(tierData.rateApplicationMethod());
              rateTier.unitOfMeasure(tierData.unitOfMeasure());
              if (tierData.applicabilityConditions() != null) {
                rateTier
                    .applicabilityConditions(new ProductCdrBankingRateLendingTierApplicabilityData()
                        .additionalInfo(tierData.applicabilityConditions().additionalInfo())
                        .additionalInfoUri(tierData.applicabilityConditions().additionalInfoUri()));
              }
              tierList.add(rateTier);
            }
            to.tiers(tierList);
          }
        }).register();
  }

}
