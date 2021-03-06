package io.biza.deepthought.data.persistence.model.cdr;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import io.biza.babelfish.cdr.enumerations.BankingProductCategory;
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
import io.biza.deepthought.data.persistence.model.ProductData;
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
@Table(name = "PRODUCT_CDR_BANKING")
public class ProductCdrBankingData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_ID")
  @ToString.Exclude
  ProductData product;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  Set<ProductCdrBankingFeatureData> feature;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  Set<ProductCdrBankingConstraintData> constraint;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<ProductCdrBankingEligibilityData> eligibility;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<ProductCdrBankingFeeData> fee;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<ProductCdrBankingRateDepositData> depositRate;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<ProductCdrBankingRateLendingData> lendingRate;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<ProductCdrBankingCardArtData> cardArt;

  @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = true)
  private ProductCdrBankingAdditionalInformationData additionalInformation;

  @Column(name = "EFFECTIVE_FROM")
  OffsetDateTime effectiveFrom;

  @Column(name = "EFFECTIVE_TO")
  OffsetDateTime effectiveTo;

  @Column(name = "LAST_UPDATED", nullable = false)
  @UpdateTimestamp
  @Builder.Default
  OffsetDateTime lastUpdated = OffsetDateTime.now();

  @Column(name = "PRODUCT_CATEGORY", nullable = false)
  @Enumerated(EnumType.STRING)
  BankingProductCategory productCategory;

  @Column(name = "APPLICATION_URI")
  @Convert(converter = URIDataConverter.class)
  URI applicationUri;

  @Column(name = "IS_TAILORED", nullable = false)
  @NotNull
  @NonNull
  @Type(type = "true_false")
  @Builder.Default
  Boolean isTailored = false;

  @PrePersist
  public void prePersist() {
    if (this.feature != null) {
      for (ProductCdrBankingFeatureData one : this.feature) {
        one.product(this);
      }
    }
    if (this.constraint != null) {
      for (ProductCdrBankingConstraintData one : this.constraint) {
        one.product(this);
      }
    }
    if (this.eligibility != null) {
      for (ProductCdrBankingEligibilityData one : this.eligibility) {
        one.product(this);
      }
    }
    if (this.fee != null) {
      for (ProductCdrBankingFeeData one : this.fee) {
        one.product(this);
      }
    }
    if (this.depositRate != null) {
      for (ProductCdrBankingRateDepositData one : this.depositRate) {
        one.product(this);
      }
    }
    if (this.lendingRate != null) {
      for (ProductCdrBankingRateLendingData one : this.lendingRate) {
        one.product(this);
      }
    }
    if (this.cardArt != null) {
      for (ProductCdrBankingCardArtData one : this.cardArt) {
        one.product(this);
      }
    }
    if (this.additionalInformation() != null) {
      this.additionalInformation().product(this);
    }

    if (this.product != null) {
      this.product.cdrBanking(this);
    }
  }

}
