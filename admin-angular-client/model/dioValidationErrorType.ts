/**
 * Deep Thought Administration API
 * This is the Deep Thought Administration API. You can find out more about Deep Thought at [https://github.com/bizaio/deepthought](https://github.com/bizaio/deepthought) or on the [DataRight.io Slack, #oss](https://join.slack.com/t/datarightio/shared_invite/enQtNzAyNTI2MjA2MzU1LTU1NGE4MmQ2N2JiZWI2ODA5MTQ2N2Q0NTRmYmM0OWRlM2U5YzA3NzU5NDYyODlhNjRmNzU3ZDZmNTI0MDE3NjY).
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

/**
 * Deep Thought Exception Type
 */
export type DioValidationErrorType = 'ATTRIBUTE_INVALID' | 'INVALID_JSON' | 'INVALID_FORMAT' | 'DATABASE_ERROR';

export const DioValidationErrorType = {
    ATTRIBUTEINVALID: 'ATTRIBUTE_INVALID' as DioValidationErrorType,
    INVALIDJSON: 'INVALID_JSON' as DioValidationErrorType,
    INVALIDFORMAT: 'INVALID_FORMAT' as DioValidationErrorType,
    DATABASEERROR: 'DATABASE_ERROR' as DioValidationErrorType
};