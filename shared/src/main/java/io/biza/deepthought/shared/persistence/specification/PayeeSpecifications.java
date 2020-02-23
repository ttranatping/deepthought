package io.biza.deepthought.shared.persistence.specification;

import java.util.UUID;
import javax.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import io.biza.babelfish.cdr.enumerations.BankingPayeeTypeWithAll;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeData_;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData_;
import io.biza.deepthought.shared.persistence.model.bank.payments.PayeeData;
import io.biza.deepthought.shared.persistence.model.customer.CustomerData;

public class PayeeSpecifications {

  public static Specification<PayeeData> customerId(UUID customerId) {
    return (root, query, cb) -> {
      Join<PayeeData, CustomerData> customerJoin = root.join(PayeeData_.customer);
      return cb.equal(customerJoin.get(CustomerData_.id), customerId);
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
