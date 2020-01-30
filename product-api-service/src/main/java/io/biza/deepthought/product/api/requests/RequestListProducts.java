package io.biza.deepthought.product.api.requests;

import java.time.OffsetDateTime;
import javax.validation.constraints.Min;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.babelfish.cdr.enumerations.BankingProductEffectiveWithAll;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestListProducts {
  
  BankingProductEffectiveWithAll effective;
  OffsetDateTime updatedSince;
  String brand;
  BankingProductCategory productCategory;
  @Min(1)
  Integer page;
  @Min(0)
  @Builder.Default
  Integer pageSize = 25;

}

