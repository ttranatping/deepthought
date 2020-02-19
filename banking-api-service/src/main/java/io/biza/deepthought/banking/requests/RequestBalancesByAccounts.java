package io.biza.deepthought.banking.requests;

import javax.validation.constraints.Min;
import io.biza.babelfish.cdr.models.requests.RequestAccountIdsV1;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RequestBalancesByAccounts {
  RequestAccountIdsV1 accountIds;
  @Min(1)
  Integer page;
  @Min(0)
  @Builder.Default
  Integer pageSize = 25;
}

