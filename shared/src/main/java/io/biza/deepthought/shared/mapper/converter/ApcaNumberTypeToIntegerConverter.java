package io.biza.deepthought.shared.mapper.converter;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.biza.babelfish.cdr.support.customtypes.ApcaNumberType;
import io.biza.deepthought.shared.persistence.model.bank.BankBranchData;
import io.biza.deepthought.shared.persistence.repository.BankBranchRepository;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

@Service
public class ApcaNumberTypeToIntegerConverter extends BidirectionalConverter<ApcaNumberType, Integer> {
  
  @Autowired
  BankBranchRepository branchRepository;

  @Override
  public Integer convertTo(ApcaNumberType source, Type<Integer> destinationType,
      MappingContext mappingContext) {
    return Integer.parseInt(source.bsb().replaceAll("-", ""));
  }

  @Override
  public ApcaNumberType convertFrom(Integer source, Type<ApcaNumberType> destinationType,
      MappingContext mappingContext) {
    return ApcaNumberType.builder().bsb(source.toString()).build();
  }

}