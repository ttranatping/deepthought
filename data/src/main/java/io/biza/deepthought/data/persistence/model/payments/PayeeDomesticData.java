package io.biza.deepthought.data.persistence.model.payments;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingDomesticPayee;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingDomesticPayeePayId;
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
@Table(name = "PAYEE_DOMESTIC")
@EqualsAndHashCode
public class PayeeDomesticData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "PAYEE_ID")
  @ToString.Exclude
  PayeeData payee;
  
  @Column(name = "PAYEE_DOMESTIC_TYPE")
  @Enumerated(EnumType.STRING)
  PayloadTypeBankingDomesticPayee type;
  
  @Column(name = "ACCOUNT_NAME")
  String accountName;
  
  @Column(name = "ACCOUNT_BSB")
  String accountBsb;
  
  @Column(name = "ACCOUNT_NUMBER")
  String accountNumber;
  
  @Column(name = "CARD_NUMBER")
  String cardNumber;
  
  @Column(name = "PAYID_NAME")
  String payIdName;
  
  @Column(name = "PAYID_IDENTIFIER")
  String payIdIdentifier;
  
  @Column(name = "PAYID_TYPE")
  @Enumerated(EnumType.STRING)
  PayloadTypeBankingDomesticPayeePayId payIdType;
  
  
}
