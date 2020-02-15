/*******************************************************************************
 * Copyright (C) 2020 Biza Pty Ltd
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *******************************************************************************/
package io.biza.deepthought.data.translation.converter;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.util.StdConverter;
import io.biza.babelfish.cdr.Constants;

/**
 * A Jackson StdConverter implementation for converting between BSB String format and Integer
 * 
 * @since 0.9.6
 */
public class IntegerToBSBStringConverter extends StdConverter<String, Integer> {
  @Override
  public Integer convert(String value) {
    if (null == value || value == "")
      return null;
    
    Matcher match = Pattern.compile("^([0-9]{3})-([0-9]{3})$").matcher(value);
    
    if(match.find()) {
      String bsbNumber = match.group(1) + match.group(2);
      return Integer.parseInt(bsbNumber);
    }
    
    return null;
  }
}
