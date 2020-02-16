package io.biza.deepthought.data.persistence.model.person;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioPhoneType;
import io.biza.deepthought.data.enumerations.DioSchemeType;
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
@Table(name = "PERSON_PHONE")
public class PersonPhoneData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @ManyToOne
  @JoinColumn(name = "PERSON_ID", nullable = false)
  @ToString.Exclude
  private PersonData person;

  @Column(name = "IS_PREFERRED", nullable = false)
  @NotNull
  @Type(type = "true_false")
  @Builder.Default
  Boolean isPreferred = false;
  
  @Column(name = "PHONE_TYPE")
  @Enumerated(EnumType.STRING)
  DioPhoneType phoneType;
  
  @Column(name = "FULL_NUMBER")
  @NotNull
  String fullNumber;
  
}
