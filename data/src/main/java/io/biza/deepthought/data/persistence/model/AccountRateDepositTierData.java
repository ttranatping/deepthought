package io.biza.deepthought.data.persistence.model;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingProductRateTierApplicationMethod;
import io.biza.babelfish.cdr.enumerations.CommonUnitOfMeasureType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Table(name = "ACCOUNT_RATE_DEPOSIT_TIER")
public class AccountRateDepositTierData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_RATE_DEPOSIT_RATE_ID", nullable = false)
  @ToString.Exclude
  private AccountRateDepositData depositRate;

  @OneToOne(mappedBy = "rateTier", cascade = CascadeType.ALL, optional = true)
  private AccountRateDepositTierApplicabilityData applicabilityConditions;

  @Column(name = "NAME", length = 255, nullable = false)
  @NotNull
  @NonNull
  String name;

  @NonNull
  @NotNull
  @Column(name = "UNIT_OF_MEASURE")
  @Enumerated(EnumType.STRING)
  CommonUnitOfMeasureType unitOfMeasure;

  @NonNull
  @NotNull
  @Column(name = "MINIMUM_VALUE")
  BigDecimal minimumValue;

  @Column(name = "MAXIMUM_VALUE")
  BigDecimal maximumValue;


  @Column(name = "RATE_APPLICATION_METHOD")
  @Enumerated(EnumType.STRING)
  BankingProductRateTierApplicationMethod rateApplicationMethod;

}
