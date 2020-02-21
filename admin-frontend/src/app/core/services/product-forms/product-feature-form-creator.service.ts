import { Injectable } from '@angular/core';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';
import {
    BankingProductFeatureV1,
    DioProductFeature
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { CdrFormInput, CdrFormSelect } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { Validators } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ProductFeatureFormCreatorService implements IProductFormCreator<DioProductFeature> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();
    private featureTypeOptions = this.typeUtility.getFeatureTypeOptions();

    constructor(private typeUtility: TypeUtilityService) { }

    createForm(feature = {} as DioProductFeature) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductFeatureV1
        } = feature;

        const {
            featureType = this.featureTypeOptions[0].value,
            additionalValue = null,
            additionalInfo,
            additionalInfoUri
        } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                featureType:        new CdrFormSelect(featureType, 'Feature type', [Validators.required], this.featureTypeOptions),
                additionalValue:    new CdrFormInput(additionalValue, 'Additional value'),
                additionalInfo:     new CdrFormInput(additionalInfo, 'Additional Info'),
                additionalInfoUri:  new CdrFormInput(additionalInfoUri, 'Additional Info URI'),
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
