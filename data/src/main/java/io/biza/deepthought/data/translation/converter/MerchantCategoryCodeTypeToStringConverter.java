package io.biza.deepthought.data.translation.converter;

import java.io.IOException;
import com.opencsv.exceptions.CsvValidationException;
import io.biza.babelfish.cdr.support.customtypes.MerchantCategoryCodeType;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class MerchantCategoryCodeTypeToStringConverter extends BidirectionalConverter<MerchantCategoryCodeType, String> {

  @Override
  public String convertTo(MerchantCategoryCodeType source, Type<String> destinationType,
      MappingContext mappingContext) {
    return source.toString();
  }

  @Override
  public MerchantCategoryCodeType convertFrom(String source, Type<MerchantCategoryCodeType> destinationType,
      MappingContext mappingContext) {
    try {
      return MerchantCategoryCodeType.fromValue(source);
    } catch (CsvValidationException | IOException e) {
      return null;
    }
  }

}