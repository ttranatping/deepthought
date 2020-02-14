package io.biza.deepthought.data.persistence.model.product;

import java.net.URI;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.BankingProductDiscountEligibilityType;
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
@Table(name = "PRODUCT_BANKING_FEE_DISCOUNT_ELIGIBILITY")
public class ProductBankingFeeDiscountEligibilityData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @ManyToOne
  @JoinColumn(name = "DISCOUNT_ID", nullable = false)
  @ToString.Exclude
  private ProductBankingFeeDiscountData discount;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  private BankingProductDiscountEligibilityType discountEligibilityType;

  @Column(name = "INFO")
  @Lob
  private String additionalInfo;

  @Column(name = "URI")
  @Convert(converter = URIDataConverter.class)
  private URI additionalInfoUri;

  @Column(name = "VALUE", length = 4096)
  private String additionalValue;

}
