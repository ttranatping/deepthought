package io.biza.deepthought.data.persistence.model.payments;

import java.util.Locale;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
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
@Table(name = "PAYEE_BPAY")
@EqualsAndHashCode
public class PayeeInternationalData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "PAYEE_ID")
  @ToString.Exclude
  PayeeData payee;
  
  @Column(name = "BENEFICIARY_NAME")
  String beneficiaryName;
  
  @Column(name = "BENEFICIARY_COUNTRY")
  @Builder.Default
  Locale beneficiaryCountry = Locale.forLanguageTag("en-AU");
  
  @Column(name = "BENEFICIARY_MESSAGE")
  String beneficiaryMessage;
  
  @Column(name = "BENEFICIARY_BIC")
  String beneficiaryBic;
  
  @Column(name = "BENEFICIARY_FEDWIRE")
  String beneficiaryFedWire;
  
  @Column(name = "BENEFICIARY_SORTCODE")
  String beneficiarySortCode;
  
  @Column(name = "BENEFICIARY_CHIP")
  String beneficiaryChip;
  
  @Column(name = "BENEFICIARY_ROUTING")
  String routingNumber;
  
  @Column(name = "LEI")
  String legalEntityIdentifier;
  
  @Column(name = "BANK_COUNTRY")
  @Builder.Default
  Locale bankCountry = Locale.forLanguageTag("en-AU");
  
  @Column(name = "BANK_ACCOUNT_NUMBER")
  @NotNull
  String bankAccountNumber;
  
  @Column(name = "BANK_ADDRESS_NAME")
  String bankAddressName;
  
  @Column(name = "BANK_ADDRESS_ADDRESS")
  String bankAddressAddress;
  
}
