package io.biza.deepthought.data.translation.converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import io.biza.babelfish.cdr.Constants;
import io.biza.babelfish.cdr.support.customtypes.ApcaNumberType;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class ApcaNumberTypeToStringConverter extends BidirectionalConverter<ApcaNumberType, String> {

  @Override
  public String convertTo(ApcaNumberType source, Type<String> destinationType,
      MappingContext mappingContext) {
    return source.bsb();
  }

  @Override
  public ApcaNumberType convertFrom(String source, Type<ApcaNumberType> destinationType,
      MappingContext mappingContext) {
    return ApcaNumberType.fromValue(source);
  }

}