/*******************************************************************************
 * Copyright (C) 2020 Biza Pty Ltd
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *******************************************************************************/
package io.biza.deepthought.shared.persistence.mapper.bank.payments;

import org.springframework.beans.factory.annotation.Autowired;
import io.biza.babelfish.cdr.models.payloads.banking.account.directdebit.BankingDirectDebitV1;
import io.biza.deepthought.shared.component.service.GrantService;
import io.biza.deepthought.shared.mapper.OrikaFactoryConfigurerInterface;
import io.biza.deepthought.shared.payloads.dio.banking.DioBankAccountDirectDebit;
import io.biza.deepthought.shared.payloads.dio.common.DioCustomer;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioCustomerType;
import io.biza.deepthought.shared.persistence.model.bank.payments.DirectDebitData;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;

public class DirectDebitDataMapper implements OrikaFactoryConfigurerInterface {
  
  @Override
  public void configure(MapperFactory orikaMapperFactory) {
    orikaMapperFactory.classMap(DirectDebitData.class, DioBankAccountDirectDebit.class).fieldAToB("id", "id")
    .byDefault().register();

    orikaMapperFactory.classMap(DirectDebitData.class, BankingDirectDebitV1.class)
    .fieldAToB("authorisedEntity", "authorisedEntity")
    .fieldAToB("lastDebitDateTime", "lastDebitDateTime")
    .fieldAToB("lastDebitAmount", "lastDebitAmount")
    .exclude("accountId")
    .byDefault()
        .register();
  }
}
