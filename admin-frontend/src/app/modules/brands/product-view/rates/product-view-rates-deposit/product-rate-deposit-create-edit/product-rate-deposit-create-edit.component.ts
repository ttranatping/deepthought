import { Component, OnInit } from '@angular/core';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import {
    DioProductRateDeposit,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeManagementService } from '@app/core/services/type-management.service';
import { CdrFormArray, CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { ProductRateTierFormCreatorService } from '@app/core/services/product-forms/product-rate-tier-form-creator.service';
import { ProductDepositRateFormCreatorService } from '@app/core/services/product-forms/product-deposit-rate-form-creator.service';

@Component({
  selector: 'app-product-rate-create-edit',
  templateUrl: './product-rate-deposit-create-edit.component.html',
  styleUrls: ['./product-rate-deposit-create-edit.component.scss']
})
export class ProductRateDepositCreateEditComponent implements OnInit {

    isSubmitted = false;

    brandId: string;
    productId: string;
    rate: DioProductRateDeposit;

    rateForm: CdrFormGroup;
    tiersFormArray: CdrFormArray;

    constructor(
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
        private typeManager: TypeManagementService,
        private productDepositRateFormCreator: ProductDepositRateFormCreatorService,
        private productRateTierFormCreator: ProductRateTierFormCreatorService
    ) {}

    ngOnInit() {
        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('rate');

        this.rateForm = this.productDepositRateFormCreator.createForm(this.rate);
        this.tiersFormArray = this.rateForm.get('cdrBanking.tiers') as CdrFormArray;
        this.tiersFormArray.isVisible = false;
    }

    getConfigProp(propName, required = false) {
        if (this.config.data && this.config.data[propName]) {
            this[propName] = this.config.data[propName];
        } else if (required) {
            this.ref.close(null);
            throw new Error(`'${propName}' is required param`);
        }
    }

    addTier() {
        this.tiersFormArray.push(this.productRateTierFormCreator.createForm());
    }

    removeTier(index: number) {
        this.tiersFormArray.removeAt(index);
    }

    onCancel() {
        this.ref.close(null);
    }

    onSave() {
        this.isSubmitted = true;

        if (!this.rateForm.valid) {
            return;
        }

        const rateData = this.rateForm.getRawValue();
        rateData.cdrBanking.additionalValue = rateData.cdrBanking.additionalValue || null;

        const saving$ = this.rate
            ? this.productsApi.updateProductRateDeposit(this.brandId, this.productId, this.rate.id, rateData)
            : this.productsApi.createProductRateDeposit(this.brandId, this.productId, rateData)
        ;

        saving$.subscribe(
            (rate) => this.ref.close(rate),
            (errors) => this.rateForm.setServerErrors(errors)
        );
    }

    trackByFn(index, item) {
        return index;
    }
}
