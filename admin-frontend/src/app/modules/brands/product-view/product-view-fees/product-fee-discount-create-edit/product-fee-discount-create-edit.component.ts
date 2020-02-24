import { Component, OnInit } from '@angular/core';
import { CdrFormArray, CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { BankingProductDiscountV1 } from '@bizaoss/deepthought-admin-angular-client';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import { ProductFeeFormCreatorService } from '@app/core/services/product-forms/product-fee-form-creator.service';

@Component({
  selector: 'app-product-fee-discount-create-edit',
  templateUrl: './product-fee-discount-create-edit.component.html',
  styleUrls: ['./product-fee-discount-create-edit.component.scss']
})
export class ProductFeeDiscountCreateEditComponent implements OnInit {

    isSubmitted = false;

    discountForm: CdrFormGroup;
    eligibilitysFormArray: CdrFormArray;

    discount: BankingProductDiscountV1;

    constructor(
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productFeeFormCreator: ProductFeeFormCreatorService
    ) { }

    ngOnInit() {
        if (this.config.data && this.config.data.discount) {
            this.discount = this.config.data.discount;
        }

        this.discountForm = this.productFeeFormCreator.createDiscountForm(this.discount);
        this.eligibilitysFormArray = this.discountForm.get('eligibility') as CdrFormArray;
        this.eligibilitysFormArray.isVisible = false;
    }

    addEligibility() {
        this.eligibilitysFormArray.push(this.productFeeFormCreator.createDiscountEligibilityForm());
    }
    removeEligibility(index: number) {
        this.eligibilitysFormArray.removeAt(index);
    }

    onCancel() {
        this.ref.close(null);
    }

    onSave() {
        this.isSubmitted = true;

        if (!this.discountForm.valid) {
            return;
        }

        const data = this.discountForm.value;
        data.additionalValue = data.additionalValue || null;

        this.ref.close(data);
    }

}
