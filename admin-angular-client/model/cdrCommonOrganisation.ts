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
import { CommonOrganisationType } from './commonOrganisationType';

/**
 * CDR Organisation Attributes
 */
export interface CdrCommonOrganisation { 
    acncregistered?: boolean;
    /**
     * Australian Business Number for the organisation
     */
    abn?: string;
    /**
     * Australian Company Number for the organisation. Required only if an ACN is applicable for the organisation type
     */
    acn?: string;
    /**
     * True if registered with the ACNC.  False if not. Absent or null if not confirmed.
     */
    isACNCRegistered?: boolean;
    /**
     * [ANZSIC (2006)](http://www.abs.gov.au/anzsic) code for the organisation.
     */
    industryCode?: string;
    organisationType: CommonOrganisationType;
    /**
     * Enumeration with values from [ISO 3166 Alpha-3](https://www.iso.org/iso-3166-country-codes.html) country codes.
     */
    registeredCountry: string;
    /**
     * The date the organisation described was established
     */
    establishmentDate?: string;
}