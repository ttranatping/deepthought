package io.biza.deepthought.shared.persistence.model.bank.transaction;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.shared.payloads.dio.enumerations.DioSchemeType;
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
public class BankAccountTransactionBPAYData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TRANSACTION_ID", foreignKey = @ForeignKey(name = "BANK_TRANSACTION_BPAY_TRANSACTION_ID_FK"))
  @ToString.Exclude
  @MapsId
  BankAccountTransactionData transaction;
  
  @Column(name = "BILLER_CODE")
  String billerCode;
  
  @Column(name = "BILLER_NAME")
  String billerName;
  
  @Column(name = "CRN")
  String crn;
  
}