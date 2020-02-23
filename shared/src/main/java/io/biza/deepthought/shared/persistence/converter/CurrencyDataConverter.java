package io.biza.deepthought.shared.persistence.converter;

import java.util.Currency;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyDataConverter implements AttributeConverter<Currency, String> {

  @Override
  public String convertToDatabaseColumn(Currency entityValue) {
    return (entityValue == null) ? null : entityValue.getCurrencyCode();
  }

  @Override
  public Currency convertToEntityAttribute(String databaseValue) {
    return databaseValue != null && databaseValue.length() > 0 ? Currency.getInstance(databaseValue)
        : null;
  }
}
