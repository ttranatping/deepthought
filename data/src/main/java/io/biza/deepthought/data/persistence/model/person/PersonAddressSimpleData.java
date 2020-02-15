package io.biza.deepthought.data.persistence.model.person;

import java.util.Locale;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import io.biza.deepthought.data.Constants;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Valid
@Table(name = "CUSTOMER_PERSON_ADDRESS_SIMPLE")
@EqualsAndHashCode
public class PersonAddressSimpleData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @JsonProperty("schemeType")
  @NotNull
  @NonNull
  @Schema(description = "Scheme Type", defaultValue = "DIO_COMMON")
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.DIO_COMMON;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "PERSON_ADDRESS_ID")
  @ToString.Exclude
  PersonAddressData address;
  
  @Column(name = "MAILING_NAME")
  String mailingName;
  
  @Column(name = "ADDRESS_LINE1")
  @NotNull
  String addressLine1;
  
  @Column(name = "ADDRESS_LINE2")
  String addressLine2;

  @Column(name = "ADDRESS_LINE3")
  String addressLine3;
  
  @Column(name = "POSTCODE")
  String postcode;
  
  @Column(name = "CITY")
  String city;
  
  @Column(name = "STATE")
  String state;
  
  @Column(name = "COUNTRY")
  @Builder.Default
  Locale country = new Locale(Constants.DEFAULT_LANGUAGE, Constants.DEFAULT_LOCALE);

}
