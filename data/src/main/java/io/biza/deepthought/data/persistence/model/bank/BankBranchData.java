package io.biza.deepthought.data.persistence.model.bank;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import io.biza.deepthought.data.enumerations.DioSchemeType;
import io.biza.deepthought.data.persistence.model.BrandData;
import io.biza.deepthought.data.persistence.model.bank.account.BankAccountData;
import io.biza.deepthought.data.persistence.model.bank.transaction.BankTransactionAPCSData;
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
@Table(name = "BANK_BRANCH")
public class BankBranchData {

  @Id
  @Column(name = "ID", insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Type(type = "uuid-char")
  UUID id;
  
  @Transient
  @Builder.Default
  private DioSchemeType schemeType = DioSchemeType.DIO_BANKING;
  
  @ManyToOne
  @JoinColumn(name = "BRAND_ID")
  @ToString.Exclude
  BrandData brand;
  
  @OneToMany(mappedBy = "branch")
  @ToString.Exclude
  Set<BankAccountData> accounts;
  
  @Column(name = "BSB", unique = true)
  @NotNull
  Integer bsb;
  
  @Column(name = "BANK_NAME")
  @NotNull
  String bankName;
  
  @Column(name = "BRANCH_NAME")
  @NotNull
  String branchName;
  
  @Column(name = "BRANCH_ADDRESS")
  @NotNull
  String branchAddress;
  
  @Column(name = "BRANCH_CITY")
  @NotNull
  String branchCity;
  
  @Column(name = "BRANCH_STATE")
  @NotNull
  String branchState;
  
  @Column(name = "BRANCH_POSTCODE")
  @NotNull
  String branchPostcode;
  
}
