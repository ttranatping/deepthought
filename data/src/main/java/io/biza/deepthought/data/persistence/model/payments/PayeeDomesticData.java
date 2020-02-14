package io.biza.deepthought.data.persistence.model.payments;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.AddressPAFFlatUnitType;
import io.biza.babelfish.cdr.enumerations.AddressPAFFloorLevelType;
import io.biza.babelfish.cdr.enumerations.AddressPAFPostalDeliveryType;
import io.biza.babelfish.cdr.enumerations.AddressPAFStateType;
import io.biza.babelfish.cdr.enumerations.AddressPAFStreetSuffix;
import io.biza.babelfish.cdr.enumerations.AddressPAFStreetType;
import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.babelfish.cdr.enumerations.CommonEmailAddressPurpose;
import io.biza.babelfish.cdr.enumerations.PayloadTypeAddress;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingDomesticPayee;
import io.biza.babelfish.cdr.enumerations.PayloadTypeBankingDomesticPayeePayId;
import io.biza.deepthought.data.enumerations.DioCustomerType;
import io.biza.deepthought.data.enumerations.DioPersonPrefix;
import io.biza.deepthought.data.enumerations.DioPersonSuffix;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.product.ProductBankingRateLendingTierData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
