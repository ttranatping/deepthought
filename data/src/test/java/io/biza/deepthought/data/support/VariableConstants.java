package io.biza.deepthought.data.support;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Currency;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.BankingProductConstraintType;
import io.biza.babelfish.cdr.enumerations.BankingProductDepositRateType;
import io.biza.babelfish.cdr.enumerations.BankingProductDiscountEligibilityType;
import io.biza.babelfish.cdr.enumerations.BankingProductDiscountType;
import io.biza.babelfish.cdr.enumerations.BankingProductEligibilityType;
import io.biza.babelfish.cdr.enumerations.BankingProductFeatureType;
import io.biza.babelfish.cdr.enumerations.BankingProductFeeType;
import io.biza.babelfish.cdr.enumerations.BankingProductLendingRateInterestPaymentType;
import io.biza.babelfish.cdr.enumerations.BankingProductLendingRateType;
import io.biza.babelfish.cdr.enumerations.BankingProductRateTierApplicationMethod;
import io.biza.babelfish.cdr.enumerations.CommonUnitOfMeasureType;

public class VariableConstants {
  public static final String PRODUCT_BUNDLE_ADDITIONAL_INFO = "Product Bundle Additional Info";
  public static final URI PRODUCT_BUNDLE_ADDITIONAL_INFO_URI =
      URI.create("http://acmebank.com.au/bundle/additional/info");
  public static final String BRAND_NAME = "Brand Name";
  public static final String BRAND_DISPLAY_NAME = "Brand Display Name";
  public static final String PRODUCT_BUNDLE_DESCRIPTION = "Product Bundle Description";
  public static final String PRODUCT_BUNDLE_NAME = "Product Bundle Name";
  public static final String PRODUCT_DESCRIPTION = "Product Description";
  public static final String PRODUCT_NAME = "Product Name";
  public static final BankingProductCategory PRODUCT_CATEGORY =
      BankingProductCategory.TRANS_AND_SAVINGS_ACCOUNTS;
  public static final Boolean PRODUCT_ISTAILORED = false;
  public static final OffsetDateTime PRODUCT_EFFECTIVE_FROM =
      OffsetDateTime.of(2020, 01, 01, 00, 00, 00, 00, ZoneOffset.UTC);
  public static final OffsetDateTime PRODUCT_EFFECTIVE_TO =
      OffsetDateTime.of(2030, 01, 01, 00, 00, 00, 00, ZoneOffset.UTC);
  public static final OffsetDateTime PRODUCT_LAST_UPDATED =
      OffsetDateTime.of(2020, 01, 14, 00, 00, 00, 00, ZoneOffset.UTC);
  public static final URI PRODUCT_ADDITIONAL_INFO_OVERVIEW_URI =
      URI.create("http://www.acme.com.au/product/additional/overview");
  public static final URI PRODUCT_ADDITIONAL_INFO_TERMS_URI =
      URI.create("http://www.acme.com.au/product/additional/terms");
  public static final URI PRODUCT_ADDITIONAL_INFO_ELIGIBILITY_URI =
      URI.create("http://www.acme.com.au/product/additional/eligibility");
  public static final URI PRODUCT_ADDITIONAL_INFO_FEES_URI =
      URI.create("http://www.acme.com.au/product/additional/fees");
  public static final URI PRODUCT_ADDITIONAL_INFO_BUNDLE_URI =
      URI.create("http://www.acme.com.au/product/additional/bundle");
  public static final String PRODUCT_CARDART_TITLE = "Card Art Title";
  public static final URI PRODUCT_CARDART_URI =
      URI.create("http://www.acme.com.au/product/cardart/image");
  public static final URI PRODUCT_APPLICATION_URI =
      URI.create("http://www.acme.com.au/product/application");
  public static final BankingProductFeatureType PRODUCT_FEATURE_TYPE =
      BankingProductFeatureType.ADDITIONAL_CARDS;
  public static final String PRODUCT_FEATURE_ADDITIONAL_VALUE = "10";
  public static final String PRODUCT_FEATURE_ADDITIONAL_INFO =
      "Additional Information Regarding Additional Cards";
  public static final URI PRODUCT_FEATURE_ADDITIONAL_INFO_URI =
      URI.create("http://www.acme.com.au/product/feature/additional/info");
  public static final BankingProductConstraintType PRODUCT_CONSTRAINT_TYPE =
      BankingProductConstraintType.MAX_BALANCE;
  public static final String PRODUCT_CONSTRAINT_ADDITIONAL_VALUE = "100000.00";
  public static final String PRODUCT_CONSTRAINT_ADDITIONAL_INFO =
      "Additional Information Regarding Maximum Balance Limit";
  public static final URI PRODUCT_CONSTRAINT_ADDITIONAL_INFO_URI =
      URI.create("http://www.acme.com.au/product/constraint/additional/info");
  public static final BankingProductEligibilityType PRODUCT_ELIGIBILITY_TYPE =
      BankingProductEligibilityType.MIN_AGE;
  public static final String PRODUCT_ELIGIBILITY_ADDITIONAL_VALUE = "18";
  public static final String PRODUCT_ELIGIBILITY_ADDITIONAL_INFO = "Must be 18 years or older";
  public static final URI PRODUCT_ELIGIBILITY_ADDITIONAL_INFO_URI =
      URI.create("http://www.acme.com.au/product/eligibility/additional/info");
  public static final String PRODUCT_FEE1_NAME = "Monthly Account Keeping Fee";
  public static final BankingProductFeeType PRODUCT_FEE1_TYPE = BankingProductFeeType.PERIODIC;
  public static final BigDecimal PRODUCT_FEE1_AMOUNT = new BigDecimal("10.00");
  public static final Currency PRODUCT_FEE1_CURRENCY = Currency.getInstance("AUD");
  public static final String PRODUCT_FEE1_ADDITIONAL_VALUE = "P1M";
  public static final String PRODUCT_FEE1_ADDITIONAL_INFO =
      "Additional Info about Monthly Account Keeping Fee";
  public static final URI PRODUCT_FEE1_ADDITIONAL_INFO_URI =
      URI.create("http://www.acme.com.au/product/fee1/additional/info");
  public static final String PRODUCT_FEE1_DISCOUNT_DESCRIPTION =
      "Product Fee Discount for Over 18s";
  public static final BankingProductDiscountType PRODUCT_FEE1_DISCOUNT_TYPE =
      BankingProductDiscountType.ELIGIBILITY_ONLY;
  public static final BigDecimal PRODUCT_FEE1_DISCOUNT_AMOUNT = new BigDecimal("5.00");
  public static final String PRODUCT_FEE1_DISCOUNT_ADDITIONAL_VALUE = "Eligibility Criteria Apply";
  public static final String PRODUCT_FEE1_DISCOUNT_ADDITIONAL_INFO =
      "Additional Information about the Over 18s Discount";
  public static final URI PRODUCT_FEE1_DISCOUNT_ADDITIONAL_URI =
      URI.create("http://www.acme.com.au/product/fee1/discount/additional/info");
  public static final BankingProductDiscountEligibilityType PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_TYPE =
      BankingProductDiscountEligibilityType.MIN_AGE;
  public static final String PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_ADDITIONAL_VALUE = "18";
  public static final String PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_ADDITIONAL_INFO =
      "Because those over 18 are more responsible we save you $5!";
  public static final URI PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_ADDITIONAL_URI =
      URI.create("http://www.acme.com.au/product/fee1/discount/eligibility/additional/info");
  public static final BankingProductDepositRateType PRODUCT_DEPOSIT_RATE_TYPE =
      BankingProductDepositRateType.FIXED;
  public static final BigDecimal PRODUCT_DEPOSIT_RATE_RATE = new BigDecimal("0.05");
  public static final Period PRODUCT_DEPOSIT_RATE_CALCULATION_FREQUENCY = Period.ofDays(1);
  public static final Duration PRODUCT_DEPOSIT_RATE_APPLICATION_FREQUENCY = Duration.ofDays(30);
  public static final String PRODUCT_DEPOSIT_RATE_ADDITIONAL_VALUE = "P6M";
  public static final String PRODUCT_DEPOSIT_RATE_ADDITIONAL_INFO =
      "Additional Information about Deposit Rate";
  public static final URI PRODUCT_DEPOSIT_RATE_ADDITIONAL_URI =
      URI.create("http://www.acme.com.au/product/rate/deposit/additional");
  public static final String PRODUCT_DEPOSIT_RATE_TIER1_NAME = "Tier 1 Name";
  public static final CommonUnitOfMeasureType PRODUCT_DEPOSIT_RATE_TIER1_UNITOFMEASURE =
      CommonUnitOfMeasureType.DOLLAR;
  public static final BigDecimal PRODUCT_DEPOSIT_RATE_TIER1_MINIMUM_VALUE = new BigDecimal("10.00");
  public static final BigDecimal PRODUCT_DEPOSIT_RATE_TIER1_MAXIMUM_VALUE =
      new BigDecimal("100.00");
  public static final BankingProductRateTierApplicationMethod PRODUCT_DEPOSIT_RATE_TIER1_RATE_APPLICATION_METHOD =
      BankingProductRateTierApplicationMethod.WHOLE_BALANCE;
  public static final String PRODUCT_DEPOSIT_RATE_TIER1_APPLICABILITY_INFO =
      "Applicability Information";
  public static final URI PRODUCT_DEPOSIT_RATE_TIER1_APPLICABILITY_URI =
      URI.create("http://www.acme.com.au/product/rate/deposit/applicability/info");
  public static final BankingProductLendingRateType PRODUCT_LENDING_RATE_TYPE =
      BankingProductLendingRateType.FIXED;
  public static final BigDecimal PRODUCT_LENDING_RATE_RATE = new BigDecimal("0.05");
  public static final Period PRODUCT_LENDING_RATE_CALCULATION_FREQUENCY = Period.ofDays(1);
  public static final Duration PRODUCT_LENDING_RATE_APPLICATION_FREQUENCY = Duration.ofDays(30);
  public static final String PRODUCT_LENDING_RATE_ADDITIONAL_VALUE = "P6M";
  public static final String PRODUCT_LENDING_RATE_ADDITIONAL_INFO =
      "Additional Information about Lending Rate";
  public static final URI PRODUCT_LENDING_RATE_ADDITIONAL_URI =
      URI.create("http://www.acme.com.au/product/rate/lending/additional");
  public static final String PRODUCT_LENDING_RATE_TIER1_NAME = "Tier 1 Name";
  public static final CommonUnitOfMeasureType PRODUCT_LENDING_RATE_TIER1_UNITOFMEASURE =
      CommonUnitOfMeasureType.DOLLAR;
  public static final BigDecimal PRODUCT_LENDING_RATE_TIER1_MINIMUM_VALUE = new BigDecimal("10.00");
  public static final BigDecimal PRODUCT_LENDING_RATE_TIER1_MAXIMUM_VALUE =
      new BigDecimal("100.00");
  public static final BankingProductRateTierApplicationMethod PRODUCT_LENDING_RATE_TIER1_RATE_APPLICATION_METHOD =
      BankingProductRateTierApplicationMethod.WHOLE_BALANCE;
  public static final String PRODUCT_LENDING_RATE_TIER1_APPLICABILITY_INFO =
      "Applicability Information";
  public static final URI PRODUCT_LENDING_RATE_TIER1_APPLICABILITY_URI =
      URI.create("http://www.acme.com.au/product/rate/lending/applicability/info");
  public static final BankingProductLendingRateInterestPaymentType PRODUCT_LENDING_INTEREST_PAYMENT_DUE =
      BankingProductLendingRateInterestPaymentType.IN_ARREARS;
}
