package io.biza.deepthought.data.persistence.converter;

import java.net.URI;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class URIDataConverter implements AttributeConverter<URI, String> {

  @Override
  public String convertToDatabaseColumn(URI entityValue) {
    return (entityValue == null) ? null : entityValue.toString();
  }

  @Override
  public URI convertToEntityAttribute(String databaseValue) {
    return databaseValue != null && databaseValue.length() > 0 ? URI.create(databaseValue.trim())
        : null;
  }
}
