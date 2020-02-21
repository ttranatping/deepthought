import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import {
    BankingProductEligibilityV1,
    DioProductEligibility
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { CdrFormInput, CdrFormSelect } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ProductEligibilitysFormService implements IProductFormCreator<DioProductEligibility> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private eligibilityTypeOptions = this.typeUtility.getEligibilityTypeOptions();

    constructor(private typeUtility: TypeUtilityService) { }

    createForm(eligibility = {} as DioProductEligibility) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductEligibilityV1
        } = eligibility;

        const {
            eligibilityType = this.eligibilityTypeOptions[0].value,
            additionalValue,
            additionalInfo,
            additionalInfoUri
        } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                eligibilityType:    new CdrFormSelect(eligibilityType, 'Eligibility type', [Validators.required], this.eligibilityTypeOptions),
                additionalValue:    new CdrFormInput(additionalValue, 'Additional value'),
                additionalInfo:     new CdrFormInput(additionalInfo, 'Additional info'),
                additionalInfoUri:  new CdrFormInput(additionalInfoUri, 'Additional info URI'),
            })
        });

        (form.get('id') as CdrFormInput).isVisible = false;
        (form.get('schemeType') as CdrFormInput).isVisible = false;

        if (!id) {
            form.removeControl('id');
        }

        return form;
    }
}
