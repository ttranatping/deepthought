import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import {
    BankingProductRateTierApplicationMethod,
    BankingProductRateTierV1,
    CommonUnitOfMeasureType,
    FormFieldType
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { CdrFormInput, CdrFormSelect } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { TypeManagementService } from '@app/core/services/type-management.service';

@Injectable({
    providedIn: 'root'
})
export class ProductRateTierFormCreatorService implements IProductFormCreator<BankingProductRateTierV1> {

    private unitOfMeasureOptions;
    private rateTierApplicationMethodOptions;

    constructor(private typeManager: TypeManagementService) {
        this.unitOfMeasureOptions = Object.keys(CommonUnitOfMeasureType).map((key) => ({
            value: CommonUnitOfMeasureType[key],
            label: this.typeManager.getLabel(FormFieldType.COMMONUNITOFMEASURETYPE, CommonUnitOfMeasureType[key]),
        }));

        this.rateTierApplicationMethodOptions = Object.keys(BankingProductRateTierApplicationMethod).map((key) => ({
            value: BankingProductRateTierApplicationMethod[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTRATETIERAPPLICATIONMETHOD, BankingProductRateTierApplicationMethod[key]),
        }));
    }

    createForm(tier = {} as BankingProductRateTierV1): CdrFormGroup {
        const {
            name = null,
            unitOfMeasure = this.unitOfMeasureOptions[0].value,
            rateApplicationMethod = this.rateTierApplicationMethodOptions[0].value,
            minimumValue = 0,
            maximumValue = 0,
            applicabilityConditions: {
                additionalInfo = null,
                additionalInfoUri = null
            } = {}
        } = tier;

        return new CdrFormGroup({
            name:                   new CdrFormInput(name, 'Name'),
            unitOfMeasure:          new CdrFormSelect(unitOfMeasure, 'Unit of measure', [], this.unitOfMeasureOptions),
            rateApplicationMethod:  new CdrFormSelect(rateApplicationMethod, 'Rate application method', [], this.rateTierApplicationMethodOptions),
            minimumValue:           new CdrFormInput(minimumValue, 'Minimum value', [], 'number'),
            maximumValue:           new CdrFormInput(maximumValue, 'Maximum value', [], 'number'),
            applicabilityConditions: new CdrFormGroup({
                additionalInfo: new CdrFormInput(additionalInfo, 'Additional info'),
                additionalInfoUri: new CdrFormInput(additionalInfoUri, 'Additional info URI')
            })
        });
    }
}
