package io.biza.deepthought.banking.requests;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RequestTransactionsByBulk {
  OffsetDateTime oldestDateTime;
  @Builder.Default
  OffsetDateTime newestDateTime = OffsetDateTime.now();
  BigDecimal minAmount;
  BigDecimal maxAmount;
  String stringFilter;
  @Min(1)
  Integer page;
  @Min(0)
  @Builder.Default
  Integer pageSize = 25;
}

