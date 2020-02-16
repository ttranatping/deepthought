package io.biza.deepthought.data.translation.converter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import com.opencsv.exceptions.CsvValidationException;
import io.biza.babelfish.cdr.Constants;
import io.biza.babelfish.cdr.support.customtypes.ApcaNumberType;
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