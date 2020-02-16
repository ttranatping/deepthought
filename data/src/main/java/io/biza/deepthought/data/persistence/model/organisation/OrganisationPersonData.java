package io.biza.deepthought.data.persistence.model.organisation;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.persistence.model.person.PersonData;
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
@Table(name = "ORGANISATION_PERSON")
public class OrganisationPersonData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @ManyToOne
  @JoinColumn(name = "PERSON_ID", nullable = false)
  PersonData person;
  
  @ManyToOne
  @JoinColumn(name = "ORGANISATION_ID", nullable = false)
  OrganisationData organisation;
  
  @Column(name="ROLE")
  String roleName;
  
}
