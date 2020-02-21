import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import {
    BankingProductConstraintV1,
    DioProductConstraint,
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { CdrFormInput, CdrFormSelect } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ProductConstraintFormService implements IProductFormCreator<DioProductConstraint> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private constraintTypeOptions = this.typeUtility.getConstraintTypeOptions();

    constructor(private typeUtility: TypeUtilityService) { }

    createForm(constraint = {} as DioProductConstraint) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductConstraintV1
        } = constraint;

        const {
            constraintType = this.constraintTypeOptions[0].value,
            additionalValue,
            additionalInfo,
            additionalInfoUri
        } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                constraintType:     new CdrFormSelect(constraintType , 'Constraint type', [Validators.required], this.constraintTypeOptions),
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
