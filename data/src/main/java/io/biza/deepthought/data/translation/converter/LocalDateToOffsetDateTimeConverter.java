package io.biza.deepthought.data.translation.converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class LocalDateToOffsetDateTimeConverter extends BidirectionalConverter<LocalDate, OffsetDateTime> {

  @Override
  public OffsetDateTime convertTo(LocalDate source, Type<OffsetDateTime> destinationType,
      MappingContext mappingContext) {
    return OffsetDateTime.of(source, LocalTime.MIDNIGHT, ZoneOffset.UTC);
  }

  @Override
  public LocalDate convertFrom(OffsetDateTime source, Type<LocalDate> destinationType,
      MappingContext mappingContext) {
    return source.toLocalDate();
  }

}