package io.biza.deepthought.admin;

public class Labels {
  /**
   * Tag definitions
   */
  public final static String TAG_BRAND_NAME = "brand-admin";
  public final static String TAG_BRAND_DESCRIPTION = "The Brand Administration API";
  public final static String TAG_PRODUCT_NAME = "product-admin";
  public final static String TAG_PRODUCT_DESCRIPTION = "The Product Administration API";
  public final static String TAG_PRODUCT_BUNDLE_NAME = "bundle-admin";
  public final static String TAG_PRODUCT_BUNDLE_DESCRIPTION =
      "The Product Bundle Administration API";
  public final static String TAG_TYPE_NAME = "type";
  public final static String TAG_TYPE_DESCRIPTION = "Type Discovery API";

  /**
   * Security labels
   */
  public final static String SECURITY_SCHEME_NAME = "deepthought_auth";
  public final static String SECURITY_SCOPE_PRODUCT_READ = "DEEPTHOUGHT:ADMIN:PRODUCT:READ";
  public final static String OAUTH2_SCOPE_PRODUCT_READ =
      "hasAuthority('SCOPE_" + SECURITY_SCOPE_PRODUCT_READ + "')";
  public final static String SECURITY_SCOPE_PRODUCT_WRITE = "DEEPTHOUGHT:ADMIN:PRODUCT:WRITE";
  public final static String OAUTH2_SCOPE_PRODUCT_WRITE =
      "hasAuthority('SCOPE_" + SECURITY_SCOPE_PRODUCT_WRITE + "')";

  /**
   * Response codes as strings
   */
  public final static String RESPONSE_CODE_CREATED = "201";
  public final static String RESPONSE_CODE_OK = "200";
  public final static String RESPONSE_CODE_NOT_FOUND = "404";
  public final static String RESPONSE_CODE_NO_CONTENT = "204";
  public final static String RESPONSE_CODE_UNPROCESSABLE_ENTITY = "422";


  /**
   * Generic Response descriptions
   */
  public final static String RESPONSE_SUCCESSFUL_LIST =
      "Successful Response containing list of requested objects";
  public final static String RESPONSE_SUCCESSFUL_READ =
      "Successful Response containing requested object";
  public final static String RESPONSE_SUCCESSFUL_CREATE =
      "Successfully created new object with content returned";
  public final static String RESPONSE_SUCCESSFUL_DELETE =
      "Successfully deleted object specified in request with no content returned";
  public final static String RESPONSE_SUCCESSFUL_UPDATE =
      "Successfully updated object specified with updated object returned";
  public final static String RESPONSE_OBJECT_NOT_FOUND = "Requested Object could not be found";
  public final static String RESPONSE_INPUT_VALIDATION_ERROR =
      "Provided request details contains validation errors, validation errors are included in the response";

  /**
   * Error Descriptions
   */
  public static final String ERROR_INVALID_BRAND = "Invalid Brand Identifier specified";
  public static final String ERROR_INVALID_BRAND_AND_PRODUCT =
      "Invalid Brand and Product Identifier set specified";
  public static final String ERROR_UNSUPPORTED_PRODUCT_SCHEME_TYPE =
      "Specified schemeType is invalid for the specified product details";
}
