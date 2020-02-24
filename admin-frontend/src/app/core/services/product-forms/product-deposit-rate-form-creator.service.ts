import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import {
    BankingProductDepositRateV1,
    DioProductRateDeposit,
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { ProductRateTierFormCreatorService } from '@app/core/services/product-forms/product-rate-tier-form-creator.service';
import { CdrFormArray, CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import {
    CdrFormDuration,
    CdrFormInput,
    CdrFormSelect
} from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { FormArray, Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ProductDepositRateFormCreatorService implements IProductFormCreator<DioProductRateDeposit> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private depositRateTypeOptions = this.typeUtility.getDepositRateTypeOptions();

    constructor(
        private typeUtility: TypeUtilityService,
        private productRateTierFormCreator: ProductRateTierFormCreatorService
    ) { }

    createForm(depositRate = {} as DioProductRateDeposit) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductDepositRateV1
        } = depositRate;

        const {
            depositRateType = this.depositRateTypeOptions[0].value,
            rate,
            calculationFrequency,
            applicationFrequency,
            additionalValue,
            additionalInfo,
            additionalInfoUri,
            tiers = []
        } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                depositRateType:        new CdrFormSelect(depositRateType , 'Rate type', [Validators.required], this.depositRateTypeOptions),
                rate:                   new CdrFormInput(rate, 'Rate'),
                calculationFrequency:   new CdrFormDuration(calculationFrequency, 'Calculation frequency'),
                applicationFrequency:   new CdrFormDuration(applicationFrequency, 'Application frequency'),
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
