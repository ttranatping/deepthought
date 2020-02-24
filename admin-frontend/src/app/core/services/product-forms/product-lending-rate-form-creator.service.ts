import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import {
    BankingProductLendingRateV1,
    DioProductRateLending,
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormArray, CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import {
    CdrFormDuration,
    CdrFormInput,
    CdrFormSelect
} from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { FormArray, Validators } from '@angular/forms';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { ProductRateTierFormCreatorService } from '@app/core/services/product-forms/product-rate-tier-form-creator.service';

@Injectable({
    providedIn: 'root'
})
export class ProductLendingRateFormCreatorService implements IProductFormCreator<DioProductRateLending> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private lendingRateTypeOptions = this.typeUtility.getLendingRateTypeOptions();

    constructor(
        private typeUtility: TypeUtilityService,
        private productRateTierFormCreator: ProductRateTierFormCreatorService
    ) {}

    createForm(lendingRate = {} as DioProductRateLending) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductLendingRateV1
        } = lendingRate;

        const {
            lendingRateType = this.lendingRateTypeOptions[0].value,
            rate,
            comparisonRate,
            applicationFrequency,
            calculationFrequency,
            interestPaymentDue,
            additionalValue,
            additionalInfo,
            additionalInfoUri,
            tiers = []
        } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                lendingRateType:        new CdrFormSelect(lendingRateType , 'Rate type', [Validators.required], this.lendingRateTypeOptions),
                rate:                   new CdrFormInput(rate, 'Rate'),
                comparisonRate:         new CdrFormInput(comparisonRate, 'Comparison Rate'),
                applicationFrequency:   new CdrFormDuration(applicationFrequency, 'Application frequency'),
                calculationFrequency:   new CdrFormDuration(calculationFrequency, 'Calculation frequency'),
                interestPaymentDue:     new CdrFormSelect(interestPaymentDue, 'Interest Payment Due'),
                additionalValue:        new CdrFormInput(additionalValue, 'Additional value'),
                additionalInfo:         new CdrFormInput(additionalInfo, 'Additional info'),
                additionalInfoUri:      new CdrFormInput(additionalInfoUri, 'Additional info URI'),
                tiers:                  new CdrFormArray([])
            })
        });

        if (tiers && tiers.length) {
            const tiersControl = form.get('cdrBanking.tiers') as CdrFormArray;
            for (const tier of tiers) {
                tiersControl.push(this.productRateTierFormCreator.createForm(tier));
            }
        }

        (form.get('id') as CdrFormInput).isVisible = false;
        (form.get('schemeType') as CdrFormInput).isVisible = false;

        if (!id) {
            form.removeControl('id');
        }

        return form;
    }
}
