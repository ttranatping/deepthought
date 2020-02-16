package io.biza.deepthought.data.persistence.model.bank.account;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.persistence.model.bank.product.ProductBankingFeatureData;
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
@Table(name = "BANK_ACCOUNT_FEATURE")
public class BankAccountFeatureData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @ToString.Exclude
  private BankAccountData account;
  
  @ManyToOne
  @JoinColumn(name = "FEATURE_ID", nullable = false)
  private ProductBankingFeatureData feature;
  
  @Column(name = "IS_ACTIVATED", nullable = false)
  @NotNull
  @NonNull
  @Type(type = "true_false")
  @Builder.Default
  Boolean isActivated = true;
  
}
