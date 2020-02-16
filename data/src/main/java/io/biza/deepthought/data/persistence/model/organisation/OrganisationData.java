package io.biza.deepthought.data.persistence.model.organisation;

import java.time.LocalDate;
import java.util.Locale;
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
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.CommonOrganisationType;
import io.biza.deepthought.data.Constants;
import io.biza.deepthought.data.persistence.converter.LocaleDataConverter;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
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
@Table(name = "ORGANISATION")
public class OrganisationData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMER_ID")
  @ToString.Exclude
  CustomerData customer;
  
  @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<OrganisationPersonData> persons;
  
  @Column(name = "BUSINESS_NAME")
  @NotNull
  String businessName;
  
  @Column(name = "LEGAL_NAME")
  String legalName;
  
  @Column(name = "SHORT_NAME")
  String shortName;
  
  @Column(name = "ABN")
  String abn;
  
  @Column(name = "ACN")
  String acn;
  
  @Column(name = "ACNC_REGISTERED")
  @Type(type = "true_false")
  Boolean isACNC;
  
  @Column(name = "ANZSIC_CODE")
  String industryCode;
  
  @Column(name = "ORGANISATION_TYPE")
  @Enumerated(EnumType.STRING)
  CommonOrganisationType organisationType;
  
  @Column(name = "REGISTERED_COUNTRY")
  @Convert(converter = LocaleDataConverter.class)
  @Builder.Default
  Locale registeredCountry = new Locale(Constants.DEFAULT_LANGUAGE, Constants.DEFAULT_LOCALE);
  
  @Column(name = "ESTABLISHMENT_DATE")
  LocalDate establishmentDate;
  
  @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
  private Set<OrganisationAddressData> physicalAddress;

}
