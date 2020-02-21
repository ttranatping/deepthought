import { Component, OnInit } from '@angular/core';
import { DioProductCardArt, ProductAdminService } from '@bizaoss/deepthought-admin-angular-client';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import { ProductCardArtsFormService } from '@app/core/services/product-forms/product-card-arts-form.service';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';

@Component({
    selector: 'app-product-card-art-create-edit',
    templateUrl: './product-card-art-create-edit.component.html',
    styleUrls: ['./product-card-art-create-edit.component.scss'],
})
export class ProductCardArtCreateEditComponent implements OnInit {

    isSubmitted = false;

    brandId: string;
    productId: string;

    cardArt: DioProductCardArt;

    form: CdrFormGroup;

    constructor(
        private productCardArtsForm: ProductCardArtsFormService,
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
    ) { }

    ngOnInit() {
        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('cardArt');

        this.form = this.productCardArtsForm.createForm(this.cardArt);
    }

    getConfigProp(propName, required = false) {
        if (this.config.data && this.config.data[propName]) {
            this[propName] = this.config.data[propName];
        } else if (required) {
            this.ref.close(null);
            throw new Error(`'${propName}' is required param`);
        }
    }

    onCancel() {
        this.ref.close(null);
    }

    onSave() {
        this.isSubmitted = true;

        if (this.form.invalid) {
            return;
        }

        const saving$ = this.cardArt
            ? this.productsApi.updateProductCardArt(this.brandId, this.productId, this.cardArt.id, this.form.getRawValue())
            : this.productsApi.createProductCardArt(this.brandId, this.productId, this.form.getRawValue())
        ;

        saving$.subscribe(
            (cardArt) => this.ref.close(cardArt),
            (errors) => this.form.setServerErrors(errors)
        );
    }

}
