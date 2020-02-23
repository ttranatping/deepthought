package io.biza.deepthought.shared.payloads.dio.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.biza.babelfish.cdr.exceptions.LabelValueEnumValueNotSupportedException;
import io.biza.babelfish.cdr.support.LabelValueEnumInterface;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Deep Thought Bank Account Types", enumAsRef = true)
public enum DioBankAccountType implements LabelValueEnumInterface {
  // @formatter:off
  TRANS_AND_SAVINGS_ACCOUNTS("TRANS_AND_SAVINGS_ACCOUNTS", "Transaction & Savings"), 
  TERM_DEPOSITS("TERM_DEPOSITS","Term Deposits"), 
  TRAVEL_CARDS("TRAVEL_CARDS", "Travel Cards"), 
  REGULATED_TRUST_ACCOUNTS("REGULATED_TRUST_ACCOUNTS", "Regulated Trusts"), 
  RESIDENTIAL_MORTGAGES("RESIDENTIAL_MORTGAGES", "Residential Mortgages"), 
  CRED_AND_CHRG_CARDS("CRED_AND_CHRG_CARDS", "Credit and Charge Cards"), 
  PERS_LOANS("PERS_LOANS", "Personal Loans"), 
  MARGIN_LOANS("MARGIN_LOANS", "Margin Loans"), 
  LEASES("LEASES", "Leases"), 
  TRADE_FINANCE("TRADE_FINANCE","Trade Finance"), 
  OVERDRAFTS("OVERDRAFTS","Overdrafts"), 
  BUSINESS_LOANS("BUSINESS_LOANS", "Business Loans");
  // @formatter:on

  private String value;

  private String label;

  DioBankAccountType(String value, String label) {
    this.value = value;
    this.label = label;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DioBankAccountType fromValue(String text)
      throws LabelValueEnumValueNotSupportedException {
    for (DioBankAccountType b : DioBankAccountType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    throw new LabelValueEnumValueNotSupportedException(
        "Unable to identify value of DioBankAccountType from " + text,
        DioBankAccountType.class.getSimpleName(), DioBankAccountType.values(), text);
  }

  @Override
  @JsonIgnore
  public String label() {
    return label;
  }
}
