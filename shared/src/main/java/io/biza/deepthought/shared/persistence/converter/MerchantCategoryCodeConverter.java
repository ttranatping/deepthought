package io.biza.deepthought.shared.persistence.converter;

import java.io.IOException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.opencsv.exceptions.CsvValidationException;
import io.biza.babelfish.cdr.support.customtypes.MerchantCategoryCodeType;


@Converter(autoApply = true)
public class MerchantCategoryCodeConverter implements AttributeConverter<MerchantCategoryCodeType, String> {

  @Override
  public String convertToDatabaseColumn(MerchantCategoryCodeType entityValue) {
    return (entityValue == null) ? null : entityValue.toString();
  }

  @Override
  public MerchantCategoryCodeType convertToEntityAttribute(String databaseValue) {
    try {
      return databaseValue != null && databaseValue.length() > 0
          ? MerchantCategoryCodeType.fromValue(databaseValue.trim())
          : null;
    } catch (CsvValidationException | IOException e) {
      return null;
    }
  }
}
