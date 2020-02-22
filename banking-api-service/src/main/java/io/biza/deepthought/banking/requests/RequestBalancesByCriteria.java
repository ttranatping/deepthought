package io.biza.deepthought.banking.requests;

import javax.validation.constraints.Min;
import io.biza.babelfish.cdr.enumerations.BankingAccountStatusWithAll;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RequestBalancesByCriteria {
  BankingProductCategory productCategory;
  @Builder.Default
  BankingAccountStatusWithAll accountStatus = BankingAccountStatusWithAll.ALL;
  Boolean isOwned;
  @Min(1)
  Integer page;
  @Min(0)
  @Builder.Default
  Integer pageSize = 25;
}

