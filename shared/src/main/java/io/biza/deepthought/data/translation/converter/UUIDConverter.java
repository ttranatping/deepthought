package io.biza.deepthought.data.translation.converter;

import java.util.UUID;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class UUIDConverter extends BidirectionalConverter<UUID, String> {

  @Override
  public String convertTo(UUID source, Type<String> destinationType,
      MappingContext mappingContext) {
    return source.toString();
  }

  @Override
  public UUID convertFrom(String source, Type<UUID> destinationType,
      MappingContext mappingContext) {
    return UUID.fromString(source);
  }

}
