import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import {
    DioProductFeature,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { ProductFeatureFormCreatorService } from '@app/core/services/product-forms/product-feature-form-creator.service';

@Component({
    selector: 'app-product-feature-create-edit',
    templateUrl: './product-feature-create-edit.component.html',
    styleUrls: ['./product-feature-create-edit.component.scss']
})
export class ProductFeatureCreateEditComponent implements OnInit {

    isSubmitted = false;

    brandId: string;
    productId: string;
    feature: DioProductFeature;

    featureForm: CdrFormGroup;

    constructor(
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
        private productFeatureFormCreator: ProductFeatureFormCreatorService
    ) {}

    ngOnInit() {

        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('feature');

        this.featureForm = this.productFeatureFormCreator.createForm(this.feature);
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

        if (!this.featureForm.valid) {
            return;
        }

        const data = this.featureForm.getRawValue();
        data.cdrBanking.additionalValue = data.cdrBanking.additionalValue || null;

        const saving$ = this.feature
            ? this.productsApi.updateProductFeature(this.brandId, this.productId, this.feature.id, data)
            : this.productsApi.createProductFeature(this.brandId, this.productId, data)
        ;

        saving$.subscribe(
            (feature) => this.ref.close(feature),
            (errors) => this.featureForm.setServerErrors(errors)
        );
    }

}
