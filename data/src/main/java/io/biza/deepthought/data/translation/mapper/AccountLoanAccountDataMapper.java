package io.biza.deepthought.data.translation.mapper;

import java.util.ArrayList;
import java.util.List;
import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.deepthought.data.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccount;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCard;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountCreditCard;
import io.biza.deepthought.data.payloads.dio.banking.DioBankAccountLoanAccount;
import io.biza.deepthought.data.payloads.dio.common.DioAddress;
import io.biza.deepthought.data.payloads.dio.common.DioOrganisation;
import io.biza.deepthought.data.payloads.dio.common.DioCustomer;
import io.biza.deepthought.data.persistence.model.account.AccountCreditCardData;
import io.biza.deepthought.data.persistence.model.account.AccountData;
import io.biza.deepthought.data.persistence.model.account.AccountLoanAccountData;
import io.biza.deepthought.data.persistence.model.customer.CustomerAccountData;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationAddressData;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationData;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

@Slf4j
public class AccountLoanAccountDataMapper implements OrikaFactoryConfigurerInterface {

  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    
    orikaMapperFactory.classMap(AccountLoanAccountData.class, DioBankAccountLoanAccount.class).fieldAToB("id", "id")
        .byDefault().register();

  }
}
