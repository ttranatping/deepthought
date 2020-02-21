import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import {
    DioProductConstraint,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { ProductConstraintFormService } from '@app/core/services/product-forms/product-constraint-form.service';

@Component({
    selector: 'app-product-constraint-create-edit',
    templateUrl: './product-constraint-create-edit.component.html',
    styleUrls: ['./product-constraint-create-edit.component.scss']
})
export class ProductConstraintCreateEditComponent implements OnInit {

    isSubmitted = false;

    brandId: string;
    productId: string;
    constraint: DioProductConstraint;

    constraintForm: CdrFormGroup;

    constructor(
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
        private constraintFormCreator: ProductConstraintFormService
    ) { }

    ngOnInit() {
        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('constraint');

        this.constraintForm = this.constraintFormCreator.createForm(this.constraint);
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

        if (!this.constraintForm.valid) {
            return;
        }

        const saving$ = this.constraint
            ? this.productsApi.updateProductConstraint(this.brandId, this.productId, this.constraint.id, this.constraintForm.getRawValue())
            : this.productsApi.createProductConstraint(this.brandId, this.productId, this.constraintForm.getRawValue())
        ;

        saving$.subscribe(
            (constraint) => this.ref.close(constraint),
            (errors) => this.constraintForm.setServerErrors(errors)
        );
    }
}
