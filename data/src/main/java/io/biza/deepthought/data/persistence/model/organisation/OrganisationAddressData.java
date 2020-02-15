package io.biza.deepthought.data.persistence.model.organisation;

import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.babelfish.cdr.enumerations.AddressPurpose;
import io.biza.babelfish.cdr.enumerations.PayloadTypeAddress;
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
@Table(name = "ORGANISATION_ADDRESS")
@EqualsAndHashCode
public class OrganisationAddressData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "ORGANISATION_ID", nullable = false)
  @ToString.Exclude
  OrganisationData organisation;
  
  @Column(name = "PURPOSE")
  @Enumerated(EnumType.STRING)
  AddressPurpose purpose;
  
  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  PayloadTypeAddress type;
  
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, optional = true)
  OrganisationAddressSimpleData simple;
  
  @OneToOne(mappedBy = "address", cascade = CascadeType.ALL, optional = true)
  OrganisationAddressPAFData paf;
  
}
