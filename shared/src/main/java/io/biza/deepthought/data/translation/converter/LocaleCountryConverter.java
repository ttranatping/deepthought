package io.biza.deepthought.data.translation.converter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

@Slf4j
public class LocaleCountryConverter extends BidirectionalConverter<Locale, String> {

  private final Map<String, Locale> localeMap;
  
  public LocaleCountryConverter() {
    String[] countries = Locale.getISOCountries();
    localeMap = new HashMap<String, Locale>(countries.length);
    for (String country : countries) {
        Locale locale = new Locale("", country);
        localeMap.put(locale.getISO3Country().toUpperCase(), locale);
    } 
    
    LOG.info("Populated locale map with {} entries", localeMap.size());
  }
  
  
  @Override
  public String convertTo(Locale source, Type<String> destinationType,
      MappingContext mappingContext) {
    return source.getISO3Country();
  }

  @Override
  public Locale convertFrom(String source, Type<Locale> destinationType,
      MappingContext mappingContext) {
    return localeMap.get(source);
  }
  
  

}