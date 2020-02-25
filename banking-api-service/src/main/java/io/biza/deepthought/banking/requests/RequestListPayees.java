package io.biza.deepthought.banking.requests;

import javax.validation.constraints.Min;
import io.biza.babelfish.cdr.enumerations.BankingPayeeTypeWithAll;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RequestListPayees {
  @Builder.Default
  BankingPayeeTypeWithAll payeeType = BankingPayeeTypeWithAll.ALL;
  @Min(1)
  Integer page;
  @Min(0)
  @Builder.Default
  Integer pageSize = 25;
}

