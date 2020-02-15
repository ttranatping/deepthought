package io.biza.deepthought.data.persistence.model.account;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.Set;
import java.util.Currency;
import java.util.HashSet;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingTermDepositMaturityInstructions;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.deepthought.data.enumerations.DioMaturityInstructionType;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeatureData;
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
@Table(name = "ACCOUNT_TERM_DEPOSIT")
public class AccountTermDepositData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  private AccountData account;
  
  @Column(name = "AMOUNT", nullable = false)
  @NotNull
  BigDecimal amount;
  
  @Column(name = "CURRENCY")
  @Builder.Default
  Currency currency = Currency.getInstance("AUD");
  
  @Column(name = "RATE", nullable = false)
  @NotNull
  BigDecimal rate;
  
  @Column(name = "LODGEMENT", nullable = false)
  @NotNull
  OffsetDateTime lodgement;
  
  @Column(name = "TERM_LENGTH", nullable = false)
  @NotNull
  Period termLength;
  
  @Column(name = "CALCULATION_FREQUENCY", nullable = false)
  @NotNull
  Period calculationFrequency;
  
  @Column(name = "LAST_CALCULATED")
  @NotNull
  OffsetDateTime lastCalculated;
  
  @Column(name = "APPLICATION_FREQUENCY", nullable = false)
  @NotNull
  Period applicationFrequency;
  
  @Column(name = "LAST_APPLIED")
  @NotNull
  OffsetDateTime lastApplied;
  
  @Column(name = "MATURITY_INSTRUCTION", nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull
  DioMaturityInstructionType maturityInstruction;

}
