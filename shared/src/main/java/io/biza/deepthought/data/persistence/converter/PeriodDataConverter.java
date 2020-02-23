package io.biza.deepthought.data.persistence.converter;

import java.time.Period;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class PeriodDataConverter implements AttributeConverter<Period, String> {

  @Override
  public String convertToDatabaseColumn(Period entityValue) {
    return (entityValue == null) ? null : entityValue.toString();
  }

  @Override
  public Period convertToEntityAttribute(String databaseValue) {
    return databaseValue != null && databaseValue.length() > 0 ? Period.parse(databaseValue) : null;
  }
}
