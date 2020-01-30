package io.biza.deepthought.data.translation.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductDepositRate;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFee;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTier;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductRateTierApplicability;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.DioProductRateDeposit;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingFeeDiscountData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateDepositData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateDepositTierApplicabilityData;
import io.biza.deepthought.data.persistence.model.cdr.ProductCdrBankingRateDepositTierData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class ProductCdrBankingRateDepositDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {

    orikaMapperFactory
        .classMap(ProductCdrBankingRateDepositData.class, BankingProductDepositRate.class)
        .exclude("tiers").byDefault()
        .customize(new CustomMapper<ProductCdrBankingRateDepositData, BankingProductDepositRate>() {
          @Override
          public void mapAtoB(ProductCdrBankingRateDepositData from, BankingProductDepositRate to,
              MappingContext context) {

            List<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>> tierList =
                new ArrayList<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>>();

            for (ProductCdrBankingRateDepositTierData tierData : from.tiers()) {
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

    orikaMapperFactory.classMap(ProductCdrBankingRateDepositData.class, DioProductRateDeposit.class)
        .fieldAToB("id", "id").field("schemeType", "schemeType")
        .field("depositRateType", "cdrBanking.depositRateType").field("rate", "cdrBanking.rate")
        .field("applicationFrequency", "cdrBanking.applicationFrequency")
        .field("calculationFrequency", "cdrBanking.calculationFrequency")
        .field("additionalValue", "cdrBanking.additionalValue")
        .field("additionalInfo", "cdrBanking.additionalInfo")
        .field("additionalInfoUri", "cdrBanking.additionalInfoUri")
        .customize(new CustomMapper<ProductCdrBankingRateDepositData, DioProductRateDeposit>() {
          @Override
          public void mapAtoB(ProductCdrBankingRateDepositData from, DioProductRateDeposit to,
              MappingContext context) {

            List<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>> tierList =
                new ArrayList<io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?>>();

            for (ProductCdrBankingRateDepositTierData tierData : from.tiers()) {
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
          public void mapBtoA(DioProductRateDeposit from, ProductCdrBankingRateDepositData to,
              MappingContext context) {

            Set<ProductCdrBankingRateDepositTierData> tierList =
                new HashSet<ProductCdrBankingRateDepositTierData>();

            for (io.biza.babelfish.cdr.abstracts.payloads.banking.product.BankingProductRateTier<?> rateTier : from
                .cdrBanking().tiers()) {
              ProductCdrBankingRateDepositTierData tierData =
                  new ProductCdrBankingRateDepositTierData();
              tierData.maximumValue(rateTier.maximumValue());
              tierData.minimumValue(rateTier.minimumValue());
              tierData.name(rateTier.name());
              tierData.rateApplicationMethod(rateTier.rateApplicationMethod());
              tierData.unitOfMeasure(rateTier.unitOfMeasure());
              if (rateTier.applicabilityConditions() != null) {
                tierData
                    .applicabilityConditions(new ProductCdrBankingRateDepositTierApplicabilityData()
                        .additionalInfo(rateTier.applicabilityConditions().additionalInfo())
                        .additionalInfoUri(rateTier.applicabilityConditions().additionalInfoUri()));
              }
              tierList.add(tierData);
            }
            to.tiers(tierList);
          }
        }).register();
  }

}
