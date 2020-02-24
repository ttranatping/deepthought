package io.biza.deepthought.shared.persistence.specification;

import java.util.UUID;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import io.biza.babelfish.cdr.enumerations.BankingPayeeTypeWithAll;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeData_;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData_;
import io.biza.deepthought.shared.persistence.model.grant.GrantCustomerAccountData_;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeData;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData;

public class PayeeSpecifications {

  public static Specification<PayeeData> customerId(UUID... customerId) {
    return (root, query, cb) -> {
      Join<PayeeData, CustomerData> customerJoin = root.join(PayeeData_.customer);
      Predicate[] customerFilter = new Predicate[customerId.length];
      for(int i = 0; i < customerId.length; i++) {
        customerFilter[i] = cb.equal(customerJoin.get(CustomerData_.id), customerId[i]);
      }
      return cb.or(customerFilter);
    };
  }
  
  public static Specification<PayeeData> payeeType(BankingPayeeTypeWithAll payeeType) {
    return (root, query, cb) -> {
      
      if(payeeType.equals(BankingPayeeTypeWithAll.BILLER)) {
        return cb.isNotNull(root.get(PayeeData_.bpay));
      }
      
      if(payeeType.equals(BankingPayeeTypeWithAll.INTERNATIONAL)) {
        return cb.isNotNull(root.get(PayeeData_.international));
      }
      
      if(payeeType.equals(BankingPayeeTypeWithAll.DOMESTIC)) {
        return cb.isNotNull(root.get(PayeeData_.domestic));
      }
      
      return null;
    };
  }  

}
