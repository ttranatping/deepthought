package io.biza.deepthought.data.persistence.model.bank;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.payments.AuthorisedEntityData;
import io.biza.deepthought.data.persistence.model.product.ProductBundleData;
import io.biza.deepthought.data.persistence.model.product.ProductData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table(name = "BRAND")
@EqualsAndHashCode
public class BrandData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;

  @Column(name = "NAME", length = 255, nullable = false)
  @NotNull
  @NonNull
  String name;

  @Column(name = "DISPLAY_NAME", nullable = false)
  @Lob
  @NotNull
  @NonNull
  String displayName;

  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ProductData> product;

  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<ProductBundleData> bundle;
  
  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<CustomerData> customer;
  
  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<BranchData> branch;
  
  @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<AuthorisedEntityData> authorisedEntity;

  @PrePersist
  public void prePersist() {
    if (product() != null) {
      for (ProductData one : product) {
        one.brand(this);
      }
    }
    if (bundle() != null) {
      for (ProductBundleData one : bundle) {
        one.brand(this);
      }
    }
  }
}
