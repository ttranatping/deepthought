package io.biza.deepthought.shared.mapper.converter;

import java.time.LocalDate;
import io.biza.babelfish.cdr.Constants;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class LocalDateToStringConverter extends BidirectionalConverter<LocalDate, String> {

  @Override
  public String convertTo(LocalDate source, Type<String> destinationType,
      MappingContext mappingContext) {
    return source.format(Constants.CDR_DATESTRING_FORMATTER);
  }

  @Override
  public LocalDate convertFrom(String source, Type<LocalDate> destinationType,
      MappingContext mappingContext) {
    return LocalDate.parse(source, Constants.CDR_DATESTRING_FORMATTER);
  }

}