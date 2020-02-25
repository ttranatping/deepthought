package io.biza.deepthought.shared.persistence.converter;

import java.util.Locale;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class LocaleDataConverter implements AttributeConverter<Locale, String> {

  @Override
  public String convertToDatabaseColumn(Locale entityValue) {
    return (entityValue == null) ? null : entityValue.toLanguageTag();
  }

  @Override
  public Locale convertToEntityAttribute(String databaseValue) {
    return databaseValue != null && databaseValue.length() > 0
        ? Locale.forLanguageTag(databaseValue.trim())
        : null;
  }
}
