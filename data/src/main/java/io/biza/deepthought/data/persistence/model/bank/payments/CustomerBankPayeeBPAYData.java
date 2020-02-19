package io.biza.deepthought.data.persistence.model.bank.payments;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
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
@Table(name = "CUSTOMER_BANK_PAYEE_BPAY")
public class CustomerBankPayeeBPAYData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "PAYEE_ID")
  @ToString.Exclude
  CustomerBankPayeeData payee;
  
  @Column(name = "BILLER_CODE")
  String billerCode;
  
  @Column(name = "BILLER_NAME")
  String billerName;
  
  @Column(name = "CRN")
  String crn;
  
  @PrePersist
  public void prePersist() {
    if (this.payee() != null) {
      this.payee().bpay(this);
    }
  }

  
}
