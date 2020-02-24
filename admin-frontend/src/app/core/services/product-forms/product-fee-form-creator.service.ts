import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import {
    BankingProductDiscountV1,
    BankingProductFeeDiscountEligibilityV1,
    BankingProductFeeV1,
    DioProductFee,
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { CdrFormArray, CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import {
    CdrFormInput,
    CdrFormSelect
} from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ProductFeeFormCreatorService implements IProductFormCreator<DioProductFee> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private feeTypeOptions = this.typeUtility.getFeeTypeOptions();
    private discountTypeOptions = this.typeUtility.getDiscountTypeOptions();
    private discountEligibilityTypeOptions = this.typeUtility.getDiscountEligibilityTypeOptions();

    constructor(
        private typeUtility: TypeUtilityService,
    ) { }

    createForm(fee = {} as DioProductFee) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductFeeV1
        } = fee;

        const {
            feeType = this.feeTypeOptions[0].value,
            name,
            amount,
            balanceRate,
            transactionRate,
            accruedRate,
            accrualFrequency,
            currency,
            additionalValue,
            additionalInfo,
            additionalInfoUri,
            discounts = []
        } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                feeType:            new CdrFormSelect(feeType, 'Type', [Validators.required], this.feeTypeOptions),
                name:               new CdrFormInput(name, 'Name', []),
                amount:             new CdrFormInput(amount, 'Amount', []),
                balanceRate:        new CdrFormInput(balanceRate, 'Balance rate', []),
                transactionRate:    new CdrFormInput(transactionRate, 'Transaction rate', []),
                accruedRate:        new CdrFormInput(accruedRate, 'Accrued rate', []),
                accrualFrequency:   new CdrFormInput(accrualFrequency, 'Accrual frequency', []),
                currency:           new CdrFormInput(currency, 'Currency'),
                additionalValue:    new CdrFormInput(additionalValue, 'Additional value'),
                additionalInfo:     new CdrFormInput(additionalInfo, 'Additional info'),
                additionalInfoUri:  new CdrFormInput(additionalInfoUri, 'Additional info URI'),
                discounts:          new CdrFormArray([])
            })
        });

        if (discounts && discounts.length) {
            const tiersControl = form.get('cdrBanking.discounts') as CdrFormArray;
            for (const discount of discounts) {
                tiersControl.push(this.createDiscountForm(discount));
            }
        }

        (form.get('id') as CdrFormInput).isVisible = false;
        (form.get('schemeType') as CdrFormInput).isVisible = false;

        if (!id) {
            form.removeControl('id');
        }

        return form;
    }

    createDiscountForm(discount = {} as BankingProductDiscountV1): CdrFormGroup {
        const {
            discountType = this.discountTypeOptions[0].value,
            description,
            amount,
            balanceRate,
            transactionRate,
            accruedRate,
            feeRate,
            additionalValue,
            additionalInfo,
            additionalInfoUri,
            eligibility = []
        } = discount;

        const form = new CdrFormGroup({
            discountType:       new CdrFormSelect(discountType, 'Discount type', [], this.discountTypeOptions),
            description:        new CdrFormInput(description, 'Description'),
            amount:             new CdrFormInput(amount, 'Amount'),
            balanceRate:        new CdrFormInput(balanceRate, 'Balance rate'),
            transactionRate:    new CdrFormInput(transactionRate, 'Transaction rate'),
            accruedRate:        new CdrFormInput(accruedRate, 'Accrued rate'),
            feeRate:            new CdrFormInput(feeRate, 'Fee rate'),
            additionalValue:    new CdrFormInput(additionalValue, 'Additional value'),
            additionalInfo:     new CdrFormInput(additionalInfo, 'Additional info'),
            additionalInfoUri:  new CdrFormInput(additionalInfoUri, 'Additional info URI'),
            eligibility:        new CdrFormArray([])
        });

        if (eligibility && eligibility.length) {
            const eligibilityControl = form.get('eligibility') as CdrFormArray;
            for (const _eligibility of eligibility) {
                eligibilityControl.push(this.createDiscountEligibilityForm(_eligibility));
            }
        }

        return form;
    }

    createDiscountEligibilityForm(eligibility = {} as BankingProductFeeDiscountEligibilityV1): CdrFormGroup {
        const {
            discountEligibilityType = this.discountEligibilityTypeOptions[0].value,
            additionalValue = '',
            additionalInfo = '',
            additionalInfoUri = '',
        } = eligibility;

        return new CdrFormGroup({
            discountEligibilityType:    new CdrFormSelect(discountEligibilityType, 'Type', [], this.discountEligibilityTypeOptions),
            additionalValue:            new CdrFormInput(additionalValue, 'Additional value'),
            additionalInfo:             new CdrFormInput(additionalInfo, 'Additional info'),
            additionalInfoUri:          new CdrFormInput(additionalInfoUri, 'Additional info URI'),
        });
    }
}
