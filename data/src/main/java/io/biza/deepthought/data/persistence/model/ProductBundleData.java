package io.biza.deepthought.data.persistence.model;

import java.net.URI;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
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
import io.biza.deepthought.data.persistence.converter.URIDataConverter;
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
@Table(name = "PRODUCT_BUNDLE")
public class ProductBundleData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @ManyToOne
  @JoinColumn(name = "BRAND_ID", nullable = false)
  @ToString.Exclude
  private BrandData brand;

  @Column(name = "NAME", length = 255, nullable = false)
  @NotNull
  @NonNull
  String name;

  @Column(name = "DESCRIPTION", nullable = false)
  @Lob
  @NotNull
  @NonNull
  String description;

  @Column(name = "ADDITIONAL_INFO", nullable = false)
  @Lob
  String additionalInfo;

  @Column(name = "ADDITIONAL_INFO_URI")
  @Convert(converter = URIDataConverter.class)
  URI additionalInfoUri;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "PRODUCT_BUNDLE_PRODUCT", joinColumns = {@JoinColumn(name = "PRODUCT_ID")},
      inverseJoinColumns = {@JoinColumn(name = "BUNDLE_ID")})
  @Builder.Default
  Set<ProductData> products = new HashSet<ProductData>();

  @PrePersist
  public void prePersist() {
    if (this.brand() != null) {
      Set<ProductBundleData> set = new HashSet<ProductBundleData>();
      if (this.brand().bundle() != null) {
        set.addAll(this.brand.bundle());
      }
      set.add(this);
      this.brand().bundle(set);
    }
  }
}
