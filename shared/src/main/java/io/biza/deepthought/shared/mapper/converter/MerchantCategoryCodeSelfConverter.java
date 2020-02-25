package io.biza.deepthought.shared.mapper.converter;

import io.biza.babelfish.cdr.support.customtypes.MerchantCategoryCodeType;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class MerchantCategoryCodeSelfConverter extends BidirectionalConverter<MerchantCategoryCodeType, MerchantCategoryCodeType> {

  @Override
  public MerchantCategoryCodeType convertTo(MerchantCategoryCodeType source, Type<MerchantCategoryCodeType> destinationType,
      MappingContext mappingContext) {
    return source;
  }

  @Override
  public MerchantCategoryCodeType convertFrom(MerchantCategoryCodeType source, Type<MerchantCategoryCodeType> destinationType,
      MappingContext mappingContext) {
    return source;
  }

}