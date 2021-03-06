package io.biza.deepthought.data.persistence.model.cdr;

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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
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
@Table(name = "PRODUCT_CDR_BANKING_ADDITIONAL_INFORMATION")
public class ProductCdrBankingAdditionalInformationData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.CDR_BANKING;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_CDR_ID")
  @ToString.Exclude
  ProductCdrBankingData product;

  @Column(name = "OVERVIEW_URI")
  @Convert(converter = URIDataConverter.class)
  URI overviewUri;

  @Column(name = "TERMS_URI")
  @Convert(converter = URIDataConverter.class)
  URI termsUri;

  @Column(name = "ELIGIBILITY_URI")
  @Convert(converter = URIDataConverter.class)
  URI eligibilityUri;

  @Column(name = "FEES_PRICING_URI")
  @Convert(converter = URIDataConverter.class)
  URI feesPricingUri;

  @Column(name = "BUNDLE_URI")
  @Convert(converter = URIDataConverter.class)
  URI bundleUri;

  @PrePersist
  public void prePersist() {
    if(this.product() != null) {
      this.product.additionalInformation(this);
    }
  }
}
