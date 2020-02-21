import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import {
    DioProductEligibility,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { ProductEligibilitysFormService } from '@app/core/services/product-forms/product-eligibilitys-form.service';

@Component({
  selector: 'app-product-eligibility-create-edit',
  templateUrl: './product-eligibility-create-edit.component.html',
  styleUrls: ['./product-eligibility-create-edit.component.scss']
})
export class ProductEligibilityCreateEditComponent implements OnInit {

    isSubmitted = false;

    brandId: string;
    productId: string;
    eligibility: DioProductEligibility;

    eligibilityForm: CdrFormGroup;

    constructor(
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
        private eligibilityFormCreator: ProductEligibilitysFormService
    ) { }

    ngOnInit() {
        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('eligibility');

        this.eligibilityForm = this.eligibilityFormCreator.createForm(this.eligibility);
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

        if (!this.eligibilityForm.valid) {
            return;
        }

        const saving$ = this.eligibility
            ? this.productsApi.updateProductEligibility(this.brandId, this.productId, this.eligibility.id, this.eligibilityForm.getRawValue())
            : this.productsApi.createProductEligibility(this.brandId, this.productId, this.eligibilityForm.getRawValue())
        ;

        saving$.subscribe(
            (eligibility) => this.ref.close(eligibility),
            (errors) => this.eligibilityForm.setServerErrors(errors)
        );
    }

}
