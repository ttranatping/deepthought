package io.biza.deepthought.data.persistence.model.transaction;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Valid
@Table(name = "TRANSACTION_BPAY")
@EqualsAndHashCode
public class TransactionCardData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TRANSACTION_ID")
  @ToString.Exclude
  TransactionData transaction;
  
  @Column(name = "MERCHANT_NAME")
  String merchantName;
  
  @Column(name = "MERCHANT_CATEGORY")
  String merchantCategoryCode;
  
}
