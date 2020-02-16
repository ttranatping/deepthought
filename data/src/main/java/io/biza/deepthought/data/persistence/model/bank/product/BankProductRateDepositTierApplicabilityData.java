package io.biza.deepthought.data.persistence.model.bank.product;

import java.net.URI;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
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
@Table(name = "BANK_PRODUCT_RATE_DEPOSIT_TIER_APPLICABILITY")
public class BankProductRateDepositTierApplicabilityData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEPOSIT_TIER_ID")
  @ToString.Exclude
  BankProductRateDepositTierData rateTier;

  @Column(name = "ADDITIONAL_INFO")
  @Lob
  String additionalInfo;

  @Column(name = "ADDITIONAL_INFO_URI")
  @Convert(converter = URIDataConverter.class)
  URI additionalInfoUri;

}
