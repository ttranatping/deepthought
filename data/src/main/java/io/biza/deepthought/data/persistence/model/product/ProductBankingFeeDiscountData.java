package io.biza.deepthought.data.persistence.model.product;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Set;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingProductDiscountType;
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
@Table(name = "PRODUCT_BANKING_FEE_DISCOUNT")
public class ProductBankingFeeDiscountData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @ManyToOne
  @JoinColumn(name = "FEE_ID", nullable = false)
  @ToString.Exclude
  private ProductBankingFeeData fee;

  @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
  private Set<ProductBankingFeeDiscountEligibilityData> eligibility;

  @Column(name = "DESCRIPTION", length = 2048)
  private String description;

  @Column(name = "DISCOUNT_TYPE")
  @Enumerated(EnumType.STRING)
  private BankingProductDiscountType discountType;

  @Column(name = "AMOUNT", precision = 24, scale = 8)
  private BigDecimal amount;

  @Column(name = "BALANCE_RATE", precision = 17, scale = 16)
  private BigDecimal balanceRate;

  @Column(name = "TRANSACTION_RATE", precision = 17, scale = 16)
  private BigDecimal transactionRate;

  @Column(name = "ACCRUED_RATE", precision = 17, scale = 16)
  private BigDecimal accruedRate;

  @Column(name = "FEE_RATE", precision = 17, scale = 16)
  private BigDecimal feeRate;

  @Column(name = "INFO", length = 4096)
  private String additionalInfo;

  @Column(name = "URI")
  @Convert(converter = URIDataConverter.class)
  private URI additionalInfoUri;

  @Column(name = "VALUE", length = 4096)
  private String additionalValue;

}
