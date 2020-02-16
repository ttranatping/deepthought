package io.biza.deepthought.data.persistence.model.bank.transaction;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.support.customtypes.MerchantCategoryCodeType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.converter.MerchantCategoryCodeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "BANK_TRANSACTION_BPAY")
public class BankTransactionCardData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "TRANSACTION_ID")
  @ToString.Exclude
  BankTransactionData transaction;
  
  @Column(name = "MERCHANT_NAME")
  String merchantName;
  
  @Column(name = "MERCHANT_CATEGORY")
  @Convert(converter = MerchantCategoryCodeConverter.class)
  MerchantCategoryCodeType merchantCategoryCode;
  
}
