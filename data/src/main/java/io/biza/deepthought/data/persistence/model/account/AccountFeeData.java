package io.biza.deepthought.data.persistence.model.account;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.product.ProductBankingFeeData;
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
@Table(name = "ACCOUNT_FEE")
public class AccountFeeData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @ManyToOne
  @JoinColumn(name = "ACCOUNT_ID", nullable = false)
  @JsonIgnore
  @ToString.Exclude
  AccountData account;
  
  @ManyToOne
  @JoinColumn(name = "FEE_ID", nullable = false)
  ProductBankingFeeData fee;
  
  @Column(name = "LAST_APPLIED")
  OffsetDateTime lastApplied;

}
