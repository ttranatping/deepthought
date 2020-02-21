import { Injectable } from '@angular/core';
import { CdrFormInput, CdrFormSelect } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { BankingProductCardArtV1, DioProductCardArt } from '@bizaoss/deepthought-admin-angular-client';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { IProductFormCreator } from '@app/core/services/product-forms/product-form-creator.interface';

@Injectable({
    providedIn: 'root'
})
export class ProductCardArtsFormService implements IProductFormCreator<DioProductCardArt> {

    private schemeTypeOptions = this.typeUtility.getSchemeTypeOptions();

    constructor(private typeUtility: TypeUtilityService) {}

    createForm(cardArt = {} as DioProductCardArt) {
        const {
            id,
            schemeType = this.schemeTypeOptions[0].value,
            cdrBanking = {} as BankingProductCardArtV1
        } = cardArt;

        const { title, imageUri } = cdrBanking;

        const form = new CdrFormGroup({
            id:         new CdrFormInput(id, ''),
            schemeType: new CdrFormSelect(schemeType, 'Scheme type', [], this.schemeTypeOptions),
            cdrBanking: new CdrFormGroup({
                title:    new CdrFormInput(title, 'Title'),
                imageUri: new CdrFormInput(imageUri, 'Image URI'),
            })
        });

        (form.get('id') as CdrFormInput).isVisible = false;
        (form.get('schemeType') as CdrFormInput).isVisible = false;

        return form;
    }
}
