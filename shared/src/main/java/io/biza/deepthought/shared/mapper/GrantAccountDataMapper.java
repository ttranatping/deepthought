package io.biza.deepthought.shared.mapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import io.biza.babelfish.cdr.enumerations.BankingLoanRepaymentType;
import io.biza.babelfish.cdr.enumerations.BankingTermDepositMaturityInstructions;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingAccount;
import io.biza.babelfish.cdr.exceptions.LabelValueEnumValueNotSupportedException;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountDetailV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingCreditCardAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingLoanAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.account.BankingTermDepositAccountV1;
import io.biza.babelfish.cdr.models.payloads.banking.product.BankingProductFeatureWithActivatedV1;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.payloads.dio.banking.DioBankBranch;
import io.biza.deepthought.data.payloads.dio.common.DioCustomer;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountCreditCardData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountFeatureData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountLoanAccountData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountTermDepositData;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.grant.GrantAccountData;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class GrantAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(GrantAccountData.class, BankingAccountV1.class)
        .fieldAToB("id", "accountId").fieldAToB("account.creationDateTime", "creationDate")
        .fieldAToB("account.displayName", "displayName").fieldAToB("account.nickname", "nickname")
        .fieldAToB("account.status", "openStatus")
        .fieldAToB("account.product.cdrBanking.productCategory", "productCategory")
        .fieldAToB("account.product.name", "productName")
        .customize(new CustomMapper<GrantAccountData, BankingAccountV1>() {
          @Override
          public void mapAtoB(GrantAccountData from, BankingAccountV1 to, MappingContext context) {

            /**
             * Determine ownership
             */
            to.isOwned(false);
            from.grant().customers().iterator().next().customer().accounts()
                .forEach(customerAccount -> {
                  if (customerAccount.owner()
                      && customerAccount.account().id().equals(from.account().id())) {
                    to.isOwned(true);
                  }
                });

            /**
             * Account Number masking
             */
            String accountNumber = from.account().accountNumber().toString();
            Double maskLength = Math.ceil(Double.valueOf(accountNumber.length()) * 0.75);
            to.maskedNumber("x".repeat(maskLength.intValue())
                .concat(accountNumber.substring(maskLength.intValue(), accountNumber.length())));
          }
        }).byDefault().register();

    orikaMapperFactory.classMap(GrantAccountData.class, BankingAccountDetailV1.class)
        .fieldAToB("id", "accountId").fieldAToB("account.creationDateTime", "creationDate")
        .fieldAToB("account.displayName", "displayName").fieldAToB("account.nickname", "nickname")
        .fieldAToB("account.status", "openStatus")
        .fieldAToB("account.product.cdrBanking.productCategory", "productCategory")
        .fieldAToB("account.product.name", "productName").fieldAToB("account.branch.bsb", "bsb")
        .fieldAToB("account.accountNumber", "accountNumber")
        .fieldAToB("account.bundle.name", "bundleName")
        .fieldAToB("account.product.depositRate", "depositRates")
        .fieldAToB("account.product.lendingRate", "lendingRates")
        .fieldAToB("account.product.fee", "fees")
        .customize(new CustomMapper<GrantAccountData, BankingAccountDetailV1>() {
          @Override
          public void mapAtoB(GrantAccountData from, BankingAccountDetailV1 to,
              MappingContext context) {

            /**
             * Populate features
             */
            List<BankingProductFeatureWithActivatedV1> features =
                new ArrayList<BankingProductFeatureWithActivatedV1>();
            for (BankAccountFeatureData feature : from.account().features()) {
              features.add(BankingProductFeatureWithActivatedV1.builder()
                  .additionalInfo(feature.feature().additionalInfo())
                  .additionalInfoUri(feature.feature().additionalInfoUri())
                  .additionalValue(feature.feature().additionalValue())
                  .featureType(feature.feature().featureType()).isActivated(feature.isActivated())
                  .build());
            }

            /**
             * Determine ownership
             */
            to.isOwned(false);
            from.grant().customers().iterator().next().customer().accounts()
                .forEach(customerAccount -> {
                  if (customerAccount.owner()
                      && customerAccount.account().id().equals(from.account().id())) {
                    to.isOwned(true);
                  }
                });

            /**
             * Account Number masking
             */
            String accountNumber = from.account().accountNumber().toString();
            Double maskLength = Math.ceil(Double.valueOf(accountNumber.length()) * 0.75);
            to.maskedNumber("x".repeat(maskLength.intValue())
                .concat(accountNumber.substring(maskLength.intValue(), accountNumber.length())));

            /**
             * Account Sub type
             */
            if (from.account().termDeposits() != null) {
              to.specificAccountUType(PayloadTypeBankingAccount.TERM_DEPOSIT);
              List<BankingTermDepositAccountV1> termDepositList =
                  new ArrayList<BankingTermDepositAccountV1>();
              for (BankAccountTermDepositData termDeposit : from.account().termDeposits()) {
                BankingTermDepositAccountV1 termDepositResult = BankingTermDepositAccountV1
                    .builder().lodgementDate(termDeposit.lodgement().toLocalDate())
                    .maturityDate(termDeposit.lodgement()
                        .plusDays(termDeposit.termLength().getDays()).toLocalDate())
                    .maturityCurrency(termDeposit.currency()).build();

                try {
                  termDepositResult.maturityInstructions(BankingTermDepositMaturityInstructions
                      .fromValue(termDeposit.maturityInstruction().toString()));
                } catch (LabelValueEnumValueNotSupportedException e) {
                  LOG.warn("Unable to convert maturity instruction to CDR type: {}",
                      termDeposit.maturityInstruction().toString());
                }

                // TODO: Rate calculation for maturity amount
                termDepositList.add(termDepositResult);
              }
            } else if (from.account().creditCards() != null) {
              to.specificAccountUType(PayloadTypeBankingAccount.CREDIT_CARD);
              BankAccountCreditCardData creditCard = from.account().creditCards().iterator().next();
              to.creditCard(
                  BankingCreditCardAccountV1.builder().minPaymentAmount(creditCard.paymentMinimum())
                      .paymentCurrency(creditCard.paymentCurrency())
                      .paymentDueAmount(creditCard.paymentDueAmount())
                      .paymentDueDate(creditCard.paymentDue().toLocalDate()).build());
            } else if (from.account().loanAccounts() != null) {
              to.specificAccountUType(PayloadTypeBankingAccount.LOAN);
              BankAccountLoanAccountData loanAccount =
                  from.account().loanAccounts().iterator().next();
              BankingLoanAccountV1 loan = BankingLoanAccountV1.builder()
                  .loanEndDate(loanAccount.creationDateTime()
                      .plusDays(loanAccount.creationLength().getDays()).toLocalDate())
                  .maxRedraw(loanAccount.redrawAvailable())
                  .maxRedrawCurrency(loanAccount.currency())
                  .nextInstalmentDate(loanAccount.lastRepayment()
                      .plusDays(loanAccount.repaymentFrequency().getDays()).toLocalDate())
                  .originalLoanAmount(loanAccount.creationAmount())
                  .originalLoanCurrency(loanAccount.currency())
                  .originalStartDate(loanAccount.creationDateTime().toLocalDate())
                  .repaymentFrequency(loanAccount.repaymentFrequency()).build();

              // TODO: Rate calculations
              try {
                loan.repaymentType(
                    BankingLoanRepaymentType.fromValue(loanAccount.repaymentType().toString()));
              } catch (LabelValueEnumValueNotSupportedException e) {
                LOG.warn("Unable to convert maturity instruction to CDR type: {}",
                    loanAccount.repaymentType());
              }

              to.loan(loan);
            }


          }
        }).byDefault().register();

    /**
     * TODO: Calculate active deposit/lending rate from product definition using account balance
     * TODO: Retrieve addresses from customer record or introduce account address mapping
     */

  }
}
