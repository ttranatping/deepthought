package io.biza.deepthought.data.persistence.model.person;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioPersonPrefix;
import io.biza.deepthought.data.enumerations.DioPersonSuffix;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.customer.CustomerData;
import io.biza.deepthought.data.persistence.model.organisation.OrganisationPersonData;
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
@Table(name = "PERSON")
public class PersonData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.CDR_COMMON;
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMER_ID", foreignKey = @ForeignKey(name = "PERSON_CUSTOMER_ID_FK"))
  @ToString.Exclude
  CustomerData customer;

  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<OrganisationPersonData> organisations;
   
  @Column(name = "PREFIX")
  @Enumerated(EnumType.STRING)
  DioPersonPrefix prefix;
  
  @Column(name = "FIRST_NAME")
  String firstName;
  
  @Column(name = "MIDDLE_NAMES")
  @Builder.Default
  @ElementCollection
  @NotNull
  List<String> middleNames = List.of();
  
  @Column(name = "LAST_NAME")
  @NotEmpty
  @NotNull
  String lastName;
  
  @Column(name = "SUFFIX")
  @Enumerated(EnumType.STRING)
  DioPersonSuffix suffix;
  
  @Column(name = "OCCUPATION_CODE")
  String occupationCode;
  
  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<PersonPhoneData> phoneNumber;
  
  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<PersonEmailData> emailAddress;
  
  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  @ToString.Exclude
  Set<PersonAddressData> physicalAddress;

}
