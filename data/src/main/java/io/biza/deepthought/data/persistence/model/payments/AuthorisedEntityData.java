package io.biza.deepthought.data.persistence.model.payments;

import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.persistence.model.bank.BrandData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@Table(name = "AUTHORISED_ENTITY")
@EqualsAndHashCode
public class AuthorisedEntityData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "BRAND_ID", nullable = false)
  BrandData brand;
  
  @OneToMany(mappedBy = "authorisedEntity", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<DirectDebitData> directDebits;
  
  @Column(name = "DESCRIPTION")
  String description;
  
  @Column(name = "FINANCIAL_INSTITUTION")
  String financialInstitution;
  
  @Column(name = "ABN")
  String abn;
  
  @Column(name = "ACN")
  String acn;
  
  @Column(name = "ARBN")
  String arbn;

}
