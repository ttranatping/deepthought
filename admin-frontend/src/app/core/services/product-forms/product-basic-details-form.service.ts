import { Injectable } from '@angular/core';
import {
    CdrFormCheckbox,
    CdrFormDate,
    CdrFormInput,
    CdrFormSelect,
    CdrFormTextarea
} from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import {
    BankingProductAdditionalInformationV1,
    CdrBankingProduct,
    DioProduct
} from '@bizaoss/deepthought-admin-angular-client';
import * as moment from 'moment';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';

@Injectable({
    providedIn: 'root'
})
export class ProductBasicDetailsFormService implements IProductFormCreator<DioProduct> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private productCategoryOptions = this.typeUtility.getProductCategoryOptions();

    constructor(private typeUtility: TypeUtilityService) {}

    createForm(product = {} as DioProduct) {
        const {
            id = 'ad372c1e-8076-440d-a66c-db86a6507947',
            name,
            description,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking: {
                productCategory = this.productCategoryOptions[0].value,
                effectiveFrom,
                effectiveTo,
                applicationUri,
                tailored = false,
                additionalInformation: {
                    overviewUri,
                    termsUri,
                    eligibilityUri,
                    feesAndPricingUri,
                    bundleUri,
                } = {} as BankingProductAdditionalInformationV1
            } = {} as CdrBankingProduct
        } = product;

        const _effectiveFrom = effectiveFrom ? moment(effectiveFrom).toDate() : null;
        const _effectiveTo = effectiveTo ? moment(effectiveTo).toDate() : null;

        const form = new CdrFormGroup({
            id:             new CdrFormInput(id, 'ID'),
            name:           new CdrFormInput(name, 'Name'),
            description:    new CdrFormTextarea(description, 'Description'),
            schemeType:     new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),

            cdrBanking: new CdrFormGroup({
                productCategory:    new CdrFormSelect(productCategory, 'Product category', [], this.productCategoryOptions),
                effectiveFrom:      new CdrFormDate(_effectiveFrom, 'Effective from'),
                effectiveTo:        new CdrFormDate(_effectiveTo, 'Effective to'),
                applicationUri:     new CdrFormInput(applicationUri, 'Application URI'),
                tailored:           new CdrFormCheckbox(tailored, 'Tailored'),

                additionalInformation: new CdrFormGroup({
                    overviewUri:        new CdrFormInput(overviewUri, 'Overview URI'),
                    termsUri:           new CdrFormInput(termsUri, 'Terms URI'),
                    eligibilityUri:     new CdrFormInput(eligibilityUri, 'Eligibility URI'),
                    feesAndPricingUri:  new CdrFormInput(feesAndPricingUri, 'Fees and Pricing URI'),
                    bundleUri:          new CdrFormInput(bundleUri, 'Bundle URI'),
                })
            })
        });

        (form.get('id') as CdrFormInput).isVisible = false;
        (form.get('schemeType') as CdrFormInput).isVisible = false;

        return form;
    }

}
