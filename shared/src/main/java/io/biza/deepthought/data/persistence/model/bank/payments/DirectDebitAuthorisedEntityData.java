package io.biza.deepthought.data.persistence.model.bank.payments;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.bank.BankBranchData;
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
@Table(name = "DIRECT_DEBIT_AUTHORISED_ENTITY")
@EqualsAndHashCode
public class DirectDebitAuthorisedEntityData {

  @Id
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @ManyToOne
  @JoinColumn(name = "BRANCH_ID", foreignKey = @ForeignKey(name = "DIRECT_DEBIT_AUTHORISED_ENTITY_BRANCH_ID_FK"))
  @ToString.Exclude
  BankBranchData branch;
  
  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "DIRECT_DEBIT_ID", foreignKey = @ForeignKey(name = "DIRECT_DEBIT_AUTHORISED_ENTITY_DIRECT_DEBIT_ID_FK"))
  @ToString.Exclude
  DirectDebitData directDebit;
  
  @Column(name = "DESCRIPTION")
  String description;
  
  @Column(name = "ABN")
  String abn;
  
  @Column(name = "ACN")
  String acn;
  
  @Column(name = "ARBN")
  String arbn;
  
  @PrePersist
  public void prePersist() {
    if (this.directDebit() != null) {
      this.directDebit().authorisedEntity(this);
    }
  }

}
