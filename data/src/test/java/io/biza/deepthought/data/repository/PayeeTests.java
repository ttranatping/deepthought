package io.biza.deepthought.data.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.biza.babelfish.cdr.enumerations.BankingProductDiscountType;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingDomesticPayee;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingPayee;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.BankingPayeeDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.bpay.BankingBillerPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.domestic.BankingDomesticPayeeV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeBankDetailsV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeBeneficiaryDetailsV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.payee.international.BankingInternationalPayeeV1;
import io.biza.babelfish.cdr.support.customtypes.ApcaNumberType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankPayee;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
import io.biza.deepthought.data.persistence.model.bank.payments.BankPayeeBPAYData;
import io.biza.deepthought.data.persistence.model.bank.payments.BankPayeeData;
import io.biza.deepthought.data.persistence.model.bank.payments.BankPayeeDomesticData;
import io.biza.deepthought.data.persistence.model.bank.payments.BankPayeeInternationalData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductAdditionalInformationData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductCardArtData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductConstraintData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductEligibilityData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductFeatureData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductFeeData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductFeeDiscountData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductFeeDiscountEligibilityData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateDepositData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateDepositTierApplicabilityData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateDepositTierData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateLendingData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateLendingTierApplicabilityData;
import io.biza.deepthought.data.persistence.model.bank.product.BankProductRateLendingTierData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.person.PersonAddressData;
import io.biza.deepthought.data.persistence.model.person.PersonAddressSimpleData;
import io.biza.deepthought.data.persistence.model.person.PersonData;
import io.biza.deepthought.data.persistence.model.person.PersonEmailData;
import io.biza.deepthought.data.persistence.model.person.PersonPhoneData;
import io.biza.deepthought.data.persistence.model.product.ProductBundleData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import io.biza.deepthought.data.repository.BrandRepository;
import io.biza.deepthought.data.support.DeepThoughtJpaConfig;
import io.biza.deepthought.data.support.TranslatorInitialisation;
import io.biza.deepthought.data.support.VariableConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("Direct Debit Data Tests")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DeepThoughtJpaConfig.class},
    loader = AnnotationConfigContextLoader.class)
@Transactional
@Slf4j
public class PayeeTests extends TranslatorInitialisation {

  @Resource
  private CustomerRepository customerRepository;

  @Resource
  private PersonRepository personRepository;

  @Resource
  private BrandRepository brandRepository;

  @Resource
  private BankBranchRepository branchRepository;

  @Resource
  private BankAccountRepository accountRepository;

  @Resource
  private BankAccountCreditCardRepository accountCreditCardRepository;

  @Resource
  private BankAccountTermDepositRepository accountTermDepositRepository;

  @Resource
  private BankAccountLoanAccountRepository accountLoanAccountRepository;

  @Resource
  private ProductRepository productRepository;

  @Resource
  private ProductBankingBundleRepository productBundleRepository;

  @Resource
  private BankDirectDebitRepository directDebitRepository;

  @Resource
  private BankAuthorisedEntityRepository authorisedEntityRepository;

  @Resource
  private PayeeRepository payeeRepository;

  @Test
  public void testPayeeInternationalCreateAndCompare() {

    BankPayeeData payee = createPayeeBase();
    payee.international(
        BankPayeeInternationalData.builder().beneficiaryName(VariableConstants.PAYEE_NAME)
            .beneficiaryMessage(VariableConstants.BENEFICIARY_DESCRIPTION).build());
    payee.international().payee(payee);
    payeeRepository.save(payee);


    DioBankPayee dioAccount = mapper.getMapperFacade().map(payee, DioBankPayee.class);

    DioBankPayee dioAccountStatic = getPayeeStatic(payee).cdrBanking(BankingPayeeDetailV1.builder()
        .creationDate(payee.creationDateTime().toLocalDate()).payeeId(payee.id().toString())
        .type(PayloadTypeBankingPayee.INTERNATIONAL)
        .description(VariableConstants.PAYEE_DESCRIPTION).nickname(VariableConstants.PAYEE_NICKNAME)
        .creationDate(VariableConstants.CREATION_DATETIME.toLocalDate())
        .international(BankingInternationalPayeeV1.builder()
            .beneficiaryDetails(BankingInternationalPayeeBeneficiaryDetailsV1.builder()
                .name(VariableConstants.PAYEE_NAME).country(VariableConstants.PAYEE_COUNTRY)
                .message(VariableConstants.BENEFICIARY_DESCRIPTION).build())
            .bankDetails(BankingInternationalPayeeBankDetailsV1.builder().country(VariableConstants.PAYEE_COUNTRY).build())
            .build())
        .build());

    LOG.warn("Source: {}", dioAccount.toString());
    LOG.warn("Destination: {}", dioAccountStatic.toString());

    LOG.info("\n\n{}\n\n", createComparisonTable(dioAccount, dioAccountStatic));

    if (!dioAccount.equals(dioAccountStatic)) {
      LOG.error("Payee International record to string reports: {}", payee.toString());
      fail("Payload conversion did not provide equality:\n"
          + createComparisonTable(dioAccount, dioAccountStatic) + "\nDB        : "
          + payee.toString() + "\nDB Convert: " + dioAccount.toString() + "\nStatic    : "
          + dioAccountStatic.toString());
    }
  }

  @Test
  public void testPayeeBpayCreateAndCompare() {

    BankPayeeData payee = createPayeeBase();
    payee.bpay(BankPayeeBPAYData.builder().billerCode(VariableConstants.PAYEE_BILLER_CODE)
        .billerName(VariableConstants.PAYEE_NAME).crn(VariableConstants.PAYEE_CRN).build());
    payee.bpay().payee(payee);
    payeeRepository.save(payee);


    DioBankPayee dioAccount = mapper.getMapperFacade().map(payee, DioBankPayee.class);

    DioBankPayee dioAccountStatic = getPayeeStatic(payee).cdrBanking(BankingPayeeDetailV1.builder()
        .creationDate(payee.creationDateTime().toLocalDate()).payeeId(payee.id().toString())
        .type(PayloadTypeBankingPayee.BILLER).description(VariableConstants.PAYEE_DESCRIPTION)
        .nickname(VariableConstants.PAYEE_NICKNAME)
        .creationDate(VariableConstants.CREATION_DATETIME.toLocalDate())
        .biller(BankingBillerPayeeV1.builder().billerCode(VariableConstants.PAYEE_BILLER_CODE)
            .billerName(VariableConstants.PAYEE_NAME).crn(VariableConstants.PAYEE_CRN).build())
        .build());

    LOG.warn("Source: {}", dioAccount.toString());
    LOG.warn("Destination: {}", dioAccountStatic.toString());

    LOG.info("\n\n{}\n\n", createComparisonTable(dioAccount, dioAccountStatic));

    if (!dioAccount.equals(dioAccountStatic)) {
      LOG.error("Payee BPAY record to string reports: {}", payee.toString());
      fail("Payload conversion did not provide equality:\n"
          + createComparisonTable(dioAccount, dioAccountStatic) + "\nDB        : "
          + payee.toString() + "\nDB Convert: " + dioAccount.toString() + "\nStatic    : "
          + dioAccountStatic.toString());
    }
  }

  @Test
  public void testPayeeDomesticCreateAndCompare() {

    BankPayeeData payee = createPayeeBase();
    payee.domestic(BankPayeeDomesticData.builder().accountBsb(VariableConstants.PAYEE_BSB)
        .accountName(VariableConstants.PAYEE_ACCOUNT_NAME)
        .accountNumber(VariableConstants.PAYEE_ACCOUNT_NUMBER)
        .type(PayloadTypeBankingDomesticPayee.ACCOUNT).build());
    payee.domestic().payee(payee);
    payeeRepository.save(payee);


    DioBankPayee dioAccount = mapper.getMapperFacade().map(payee, DioBankPayee.class);

    DioBankPayee dioAccountStatic = getPayeeStatic(payee).cdrBanking(BankingPayeeDetailV1.builder()
        .creationDate(payee.creationDateTime().toLocalDate()).payeeId(payee.id().toString())
        .type(PayloadTypeBankingPayee.DOMESTIC).description(VariableConstants.PAYEE_DESCRIPTION)
        .nickname(VariableConstants.PAYEE_NICKNAME)
        .creationDate(VariableConstants.CREATION_DATETIME.toLocalDate())
        .domestic(BankingDomesticPayeeV1.builder()
            .payeeAccountType(PayloadTypeBankingDomesticPayee.ACCOUNT)
            .account(BankingDomesticPayeeAccountV1.builder()
                .bsb(ApcaNumberType.fromValue(VariableConstants.PAYEE_BSB))
                .accountName(VariableConstants.PAYEE_ACCOUNT_NAME)
                .accountNumber(VariableConstants.PAYEE_ACCOUNT_NUMBER).build())
            .build())
        .build());

    LOG.warn("Source: {}", dioAccount.toString());
    LOG.warn("Destination: {}", dioAccountStatic.toString());

    LOG.info("\n\n{}\n\n", createComparisonTable(dioAccount, dioAccountStatic));

    if (!dioAccount.equals(dioAccountStatic)) {
      LOG.error("Payee Domestic record to string reports: {}", payee.toString());
      fail("Payload conversion did not provide equality:\n"
          + createComparisonTable(dioAccount, dioAccountStatic) + "\nDB        : "
          + payee.toString() + "\nDB Convert: " + dioAccount.toString() + "\nStatic    : "
          + dioAccountStatic.toString());
    }
  }

  public BankPayeeData createPayeeBase() {

    CustomerData customer = createCustomerPerson();

    BankPayeeData payee = BankPayeeData.builder().description(VariableConstants.PAYEE_DESCRIPTION)
        .nickName(VariableConstants.PAYEE_NICKNAME)
        .creationDateTime(VariableConstants.CREATION_DATETIME).build();
    payee.customer(customer);
    payeeRepository.save(payee);
    return payeeRepository.findById(payee.id()).get();
  }

  public DioBankPayee getPayeeStatic(BankPayeeData payee) {
    LOG.warn("Debit details are: {}", payee.toString());
    DioBankPayee dioPayeeStatic = DioBankPayee.builder().id(payee.id())
        .cdrBanking(BankingPayeeDetailV1.builder().description(VariableConstants.PAYEE_DESCRIPTION)
            .nickname(VariableConstants.PAYEE_NICKNAME).build())
        .build();
    return dioPayeeStatic;
  }


  public CustomerData createCustomerPerson() {

    BrandData brand = BrandData.builder().name(VariableConstants.BRAND_NAME)
        .displayName(VariableConstants.BRAND_DISPLAY_NAME).build();
    brandRepository.save(brand);

    Optional<BrandData> brandReturn = brandRepository.findById(brand.id());
    assertTrue(brandReturn.isPresent());

    CustomerData customer = CustomerData.builder().creationTime(VariableConstants.CREATION_DATETIME)
        .lastUpdated(VariableConstants.UPDATE_DATETIME).brand(brandReturn.get()).build();

    PersonData person = PersonData.builder().firstName(VariableConstants.PERSON_FIRST_NAME)
        .lastName(VariableConstants.PERSON_LAST_NAME).prefix(VariableConstants.PERSON_PREFIX)
        .middleNames(VariableConstants.PERSON_MIDDLE_NAME).suffix(VariableConstants.PERSON_SUFFIX)
        .occupationCode(VariableConstants.OCCUPATION_CODE).build();
    PersonEmailData email = PersonEmailData.builder().address(VariableConstants.DIO_EMAIL.address())
        .isPreferred(VariableConstants.DIO_EMAIL.isPreferred())
        .type(VariableConstants.DIO_EMAIL.type()).build();
    email.person(person);
    PersonPhoneData phone =
        PersonPhoneData.builder().fullNumber(VariableConstants.DIO_PHONE_NUMBER.fullNumber())
            .isPreferred(VariableConstants.DIO_PHONE_NUMBER.isPreferred())
            .phoneType(VariableConstants.DIO_PHONE_NUMBER.phoneType()).build();
    phone.person(person);
    PersonAddressSimpleData simple = PersonAddressSimpleData.builder()
        .addressLine1(VariableConstants.DIO_ADDRESS.simple().addressLine1())
        .city(VariableConstants.DIO_ADDRESS.simple().city())
        .country(VariableConstants.DIO_ADDRESS.simple().country())
        .mailingName(VariableConstants.DIO_ADDRESS.simple().mailingName())
        .postcode(VariableConstants.DIO_ADDRESS.simple().postcode())
        .state(VariableConstants.DIO_ADDRESS.simple().state()).build();
    PersonAddressData address =
        PersonAddressData.builder().purpose(VariableConstants.DIO_ADDRESS.purpose()).build();
    simple.address(address);
    address.simple(simple);

    person.emailAddress(Set.of(email));
    person.phoneNumber(Set.of(phone));
    person.physicalAddress(Set.of(address));
    person.customer(customer);
    customer.person(person);

    customerRepository.save(customer);

    return customer;
  }

  public BankBranchData createBranch() {

    BrandData brand = BrandData.builder().name(VariableConstants.BRAND_NAME)
        .displayName(VariableConstants.BRAND_DISPLAY_NAME).build();
    brandRepository.save(brand);

    Optional<BrandData> brandReturn = brandRepository.findById(brand.id());
    assertTrue(brandReturn.isPresent());

    BankBranchData branch = BankBranchData.builder().bankName(VariableConstants.BANK_NAME)
        .branchAddress(VariableConstants.BRANCH_ADDRESS).branchCity(VariableConstants.BRANCH_CITY)
        .branchName(VariableConstants.BRANCH_NAME).branchPostcode(VariableConstants.BRANCH_POSTCODE)
        .branchState(VariableConstants.BRANCH_STATE).bsb(VariableConstants.BRANCH_BSB).brand(brand)
        .build();
    branchRepository.save(branch);

    return branch;
  }

  public BrandData createBrandWithBundle() {
    BrandData brand = BrandData.builder().name(VariableConstants.BRAND_NAME)
        .displayName(VariableConstants.BRAND_DISPLAY_NAME).build();
    brandRepository.save(brand);

    Optional<BrandData> brandReturn = brandRepository.findById(brand.id());
    assertTrue(brandReturn.isPresent());

    ProductBundleData productBundle =
        ProductBundleData.builder().additionalInfo(VariableConstants.PRODUCT_BUNDLE_ADDITIONAL_INFO)
            .additionalInfoUri(VariableConstants.PRODUCT_BUNDLE_ADDITIONAL_INFO_URI)
            .description(VariableConstants.PRODUCT_BUNDLE_DESCRIPTION)
            .name(VariableConstants.PRODUCT_BUNDLE_NAME).build();
    productBundle.brand(brand);
    productBundleRepository.save(productBundle);

    return brandReturn.get();
  }


  public ProductData createProductWithTheWorks() {
    BrandData brand = createBrandWithBundle();
    ProductData product = ProductData.builder().name(VariableConstants.PRODUCT_NAME)
        .description(VariableConstants.PRODUCT_DESCRIPTION).schemeType(DioSchemeType.CDR_BANKING)
        .build();
    BankProductData cdrBanking =
        BankProductData.builder().effectiveFrom(VariableConstants.PRODUCT_EFFECTIVE_FROM)
            // Product Baseline
            .effectiveTo(VariableConstants.PRODUCT_EFFECTIVE_TO)
            .lastUpdated(VariableConstants.PRODUCT_LAST_UPDATED)
            .productCategory(VariableConstants.PRODUCT_CATEGORY)
            .applicationUri(VariableConstants.PRODUCT_APPLICATION_URI)
            .isTailored(VariableConstants.PRODUCT_ISTAILORED)
            .additionalInformation(BankProductAdditionalInformationData.builder()
                .overviewUri(VariableConstants.PRODUCT_ADDITIONAL_INFO_OVERVIEW_URI)
                .termsUri(VariableConstants.PRODUCT_ADDITIONAL_INFO_TERMS_URI)
                .eligibilityUri(VariableConstants.PRODUCT_ADDITIONAL_INFO_ELIGIBILITY_URI)
                .feesPricingUri(VariableConstants.PRODUCT_ADDITIONAL_INFO_FEES_URI)
                .bundleUri(VariableConstants.PRODUCT_ADDITIONAL_INFO_BUNDLE_URI).build())
            .cardArt(Set.of(
                BankProductCardArtData.builder().title(VariableConstants.PRODUCT_CARDART_TITLE)
                    .imageUri(VariableConstants.PRODUCT_CARDART_URI).build()))
            // Product Detail with one of basically everything
            .feature(Set.of(BankProductFeatureData.builder()
                .featureType(VariableConstants.PRODUCT_FEATURE_TYPE)
                .additionalInfo(VariableConstants.PRODUCT_FEATURE_ADDITIONAL_INFO)
                .additionalInfoUri(VariableConstants.PRODUCT_FEATURE_ADDITIONAL_INFO_URI)
                .additionalValue(VariableConstants.PRODUCT_FEATURE_ADDITIONAL_VALUE).build()))
            .constraint(Set.of(BankProductConstraintData.builder()
                .constraintType(VariableConstants.PRODUCT_CONSTRAINT_TYPE)
                .additionalInfo(VariableConstants.PRODUCT_CONSTRAINT_ADDITIONAL_INFO)
                .additionalInfoUri(VariableConstants.PRODUCT_CONSTRAINT_ADDITIONAL_INFO_URI)
                .additionalValue(VariableConstants.PRODUCT_CONSTRAINT_ADDITIONAL_VALUE).build()))
            .eligibility(Set.of(BankProductEligibilityData.builder()
                .eligibilityType(VariableConstants.PRODUCT_ELIGIBILITY_TYPE)
                .additionalInfo(VariableConstants.PRODUCT_ELIGIBILITY_ADDITIONAL_INFO)
                .additionalInfoUri(VariableConstants.PRODUCT_ELIGIBILITY_ADDITIONAL_INFO_URI)
                .additionalValue(VariableConstants.PRODUCT_ELIGIBILITY_ADDITIONAL_VALUE).build()))
            .fee(Set.of(BankProductFeeData.builder().name(VariableConstants.PRODUCT_FEE1_NAME)
                .feeType(VariableConstants.PRODUCT_FEE1_TYPE)
                .amount(VariableConstants.PRODUCT_FEE1_AMOUNT)
                .currency(VariableConstants.PRODUCT_FEE1_CURRENCY)
                .additionalValue(VariableConstants.PRODUCT_FEE1_ADDITIONAL_VALUE)
                .additionalInfo(VariableConstants.PRODUCT_FEE1_ADDITIONAL_INFO)
                .additionalInfoUri(VariableConstants.PRODUCT_FEE1_ADDITIONAL_INFO_URI)
                .discounts(Set.of(BankProductFeeDiscountData.builder()
                    .discountType(BankingProductDiscountType.ELIGIBILITY_ONLY)
                    .description(VariableConstants.PRODUCT_FEE1_DISCOUNT_DESCRIPTION)
                    .amount(VariableConstants.PRODUCT_FEE1_DISCOUNT_AMOUNT)
                    .additionalInfo(VariableConstants.PRODUCT_FEE1_DISCOUNT_ADDITIONAL_INFO)
                    .additionalValue(VariableConstants.PRODUCT_FEE1_DISCOUNT_ADDITIONAL_VALUE)
                    .additionalInfoUri(VariableConstants.PRODUCT_FEE1_DISCOUNT_ADDITIONAL_URI)
                    .eligibility(Set.of(BankProductFeeDiscountEligibilityData.builder()
                        .discountEligibilityType(
                            VariableConstants.PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_TYPE)
                        .additionalValue(
                            VariableConstants.PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_ADDITIONAL_VALUE)
                        .additionalInfo(
                            VariableConstants.PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_ADDITIONAL_INFO)
                        .additionalInfoUri(
                            VariableConstants.PRODUCT_FEE1_DISCOUNT_ELIGIBILITY_ADDITIONAL_URI)
                        .build()))
                    .build()))
                .build()))
            .depositRate(Set.of(BankProductRateDepositData.builder()
                .depositRateType(VariableConstants.PRODUCT_DEPOSIT_RATE_TYPE)
                .rate(VariableConstants.PRODUCT_DEPOSIT_RATE_RATE)
                .calculationFrequency(VariableConstants.PRODUCT_DEPOSIT_RATE_CALCULATION_FREQUENCY)
                .applicationFrequency(VariableConstants.PRODUCT_DEPOSIT_RATE_CALCULATION_FREQUENCY)
                .additionalInfo(VariableConstants.PRODUCT_DEPOSIT_RATE_ADDITIONAL_INFO)
                .additionalValue(VariableConstants.PRODUCT_DEPOSIT_RATE_ADDITIONAL_VALUE)
                .additionalInfoUri(VariableConstants.PRODUCT_DEPOSIT_RATE_ADDITIONAL_URI)
                .tiers(Set.of(BankProductRateDepositTierData.builder()
                    .name(VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_NAME)
                    .unitOfMeasure(VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_UNITOFMEASURE)
                    .minimumValue(VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_MINIMUM_VALUE)
                    .maximumValue(VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_MAXIMUM_VALUE)
                    .rateApplicationMethod(
                        VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_RATE_APPLICATION_METHOD)
                    .applicabilityConditions(BankProductRateDepositTierApplicabilityData
                        .builder()
                        .additionalInfo(
                            VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_APPLICABILITY_INFO)
                        .additionalInfoUri(
                            VariableConstants.PRODUCT_DEPOSIT_RATE_TIER1_APPLICABILITY_URI)
                        .build())
                    .build()))
                .build()))
            .lendingRate(Set.of(BankProductRateLendingData.builder()
                .lendingRateType(VariableConstants.PRODUCT_LENDING_RATE_TYPE)
                .rate(VariableConstants.PRODUCT_LENDING_RATE_RATE)
                .calculationFrequency(VariableConstants.PRODUCT_LENDING_RATE_CALCULATION_FREQUENCY)
                .applicationFrequency(VariableConstants.PRODUCT_LENDING_RATE_CALCULATION_FREQUENCY)
                .interestPaymentDue(VariableConstants.PRODUCT_LENDING_INTEREST_PAYMENT_DUE)
                .additionalInfo(VariableConstants.PRODUCT_LENDING_RATE_ADDITIONAL_INFO)
                .additionalValue(VariableConstants.PRODUCT_LENDING_RATE_ADDITIONAL_VALUE)
                .additionalInfoUri(VariableConstants.PRODUCT_LENDING_RATE_ADDITIONAL_URI)
                .tiers(Set.of(BankProductRateLendingTierData.builder()
                    .name(VariableConstants.PRODUCT_LENDING_RATE_TIER1_NAME)
                    .unitOfMeasure(VariableConstants.PRODUCT_LENDING_RATE_TIER1_UNITOFMEASURE)
                    .minimumValue(VariableConstants.PRODUCT_LENDING_RATE_TIER1_MINIMUM_VALUE)
                    .maximumValue(VariableConstants.PRODUCT_LENDING_RATE_TIER1_MAXIMUM_VALUE)
                    .rateApplicationMethod(
                        VariableConstants.PRODUCT_LENDING_RATE_TIER1_RATE_APPLICATION_METHOD)
                    .applicabilityConditions(BankProductRateLendingTierApplicabilityData
                        .builder()
                        .additionalInfo(
                            VariableConstants.PRODUCT_LENDING_RATE_TIER1_APPLICABILITY_INFO)
                        .additionalInfoUri(
                            VariableConstants.PRODUCT_LENDING_RATE_TIER1_APPLICABILITY_URI)
                        .build())
                    .build()))
                .build()))
            .build();

    product.brand(brand);
    product.cdrBanking(cdrBanking);
    product.bundle(brand.bundle());
    productRepository.save(product);

    // LOG.info("Raw Product is set to: {}", product.toString());

    return product;
  }
}
