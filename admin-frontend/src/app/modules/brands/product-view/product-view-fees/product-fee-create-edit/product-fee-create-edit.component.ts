import { Component, OnInit } from '@angular/core';
import { TypeManagementService } from '@app/core/services/type-management.service';
import {
    BankingProductDiscountV1,
    DioProductFee,
    FormFieldType,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormArray, CdrFormGroup } from '@app/shared/forms/crd-form-group.class';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, map } from 'rxjs/operators';
import { LayoutService } from '@app/layout/layout.service';
import { ProductFeeDiscountCreateEditComponent } from '../product-fee-discount-create-edit/product-fee-discount-create-edit.component';
import { ConfirmationService, DialogService } from 'primeng/api';
import { ProductFeeFormCreatorService } from '@app/core/services/product-forms/product-fee-form-creator.service';

@Component({
  selector: 'app-product-fee-create-edit',
  templateUrl: './product-fee-create-edit.component.html',
  styleUrls: ['./product-fee-create-edit.component.scss']
})
export class ProductFeeCreateEditComponent implements OnInit {

    isSubmitted = false;

    brandId: string;
    productId: string;
    feeId: string;

    fee: DioProductFee;

    feeForm: CdrFormGroup;

    discounts: BankingProductDiscountV1[] = [];
    discountsErrors: { [key: string]: string } = {};

    discountDetailsOptions: Array<{ key: string; label: string; }> = [
        { key: 'discountType', label: 'Discount type' },
        { key: 'description', label: 'Description' },
        { key: 'amount', label: 'Amount' },
        { key: 'balanceRate', label: 'Balance rate' },
        { key: 'transactionRate', label: 'Transaction rate' },
        { key: 'accruedRate', label: 'Accrued rate' },
        { key: 'feeRate', label: 'Fee rate' },
        { key: 'additionalValue', label: 'Additional value' },
        { key: 'additionalInfo', label: 'Additional info' },
        { key: 'additionalInfoUri', label: 'Additional info URI' },
    ];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private typeManager: TypeManagementService,
        private productsApi: ProductAdminService,
        private layout: LayoutService,
        private confirmationService: ConfirmationService,
        private dialogService: DialogService,
        private productFeeFormCreator: ProductFeeFormCreatorService
    ) { }

    ngOnInit() {
        const params = this.route.snapshot.paramMap;

        this.brandId = params.get('brandId');
        this.productId = params.get('productId');
        this.feeId = params.get('feeId');

        if (this.feeId) {
            this.layout.togglePageLoader.emit(true);

            this.fetchFee().pipe(
                map((fee) => this.init(fee)),
                finalize(() => this.layout.togglePageLoader.emit(false))
            ).subscribe();
        } else {
            this.init(null);
        }
    }

    init(fee) {
        this.fee = fee;
        this.feeForm = this.productFeeFormCreator.createForm(this.fee);

        const discountsControl = this.feeForm.get('cdrBanking.discounts') as CdrFormArray;
        discountsControl.isVisible = false;
        this.discounts = discountsControl.value;
    }

    fetchFee() {
        if (!this.feeId) {
            return void 0;
        }
        return this.productsApi.getProductFee(this.brandId, this.productId, this.feeId);
    }

    createEditDiscount(discount?: BankingProductDiscountV1) {
        const isNew = !discount;

        const ref = this.dialogService.open(ProductFeeDiscountCreateEditComponent, {
            header: isNew ? 'Add discount' : 'Edit discount',
            width: '40%',
            style: {
                'overflow-y': 'auto',
                'overflow-x': 'hidden',
                'max-height': '80vh',
                'min-height': '250px'
            },
            data: { discount: discount || null }
        });

        ref.onClose.subscribe((_discount) => {
            if (!_discount) return;

            if (isNew) {
                this.discounts = [...this.discounts, _discount];
            } else {
                this.discounts = this.discounts.map((_) => _ === discount ? _discount : _);
            }
        });
    }
    removeDiscount(discount: BankingProductDiscountV1) {
        this.confirmationService.confirm({
            message: `Are you sure want to remove this discount?`,
            header: 'Remove discount',
            icon: null,
            accept: () => {
                this.discounts = this.discounts.filter((_) => _ !== discount);
            },
            reject: () => {}
        });
    }

    getDiscountType(type) {
        return this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTDISCOUNTTYPE, type);
    }
    getDiscountEligibilityType(type) {
        return this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTDISCOUNTELIGIBILITYTYPE, type);
    }

    redirectToFeesPage() {
        this.router.navigate(['/brands', this.brandId, 'products', this.productId, 'fees']);
    }

    onCancel() {
        if (!this.feeForm.touched && !this.discounts.length) {
            return this.redirectToFeesPage();
        }

        this.confirmationService.confirm({
            message: `Are you sure want leave this page? Changes will be lost!`,
            header: 'Confirm cancel',
            icon: null,
            accept: () => {
                return this.redirectToFeesPage();
            },
            reject: () => {}
        });
    }

    onSave() {
        this.discountsErrors = {};
        this.isSubmitted = true;

        if (!this.feeForm.valid) {
            return;
        }

        const data = this.feeForm.getRawValue();
        data.cdrBanking.discounts = this.discounts;

        const saving$ = this.fee
            ? this.productsApi.updateProductFee(this.brandId, this.productId, this.fee.id, data)
            : this.productsApi.createProductFee(this.brandId, this.productId, data)
        ;

        saving$.subscribe(
            _ => this.redirectToFeesPage(),
            (errors) => this.onSavingError(errors)
        );

    }

    onSavingError(errors: any) {
        this.feeForm.setServerErrors(errors);

        if (errors.error.type === 'VALIDATION_ERROR') {

            for (let i = 0; i < this.discounts.length; i++) {
                for (const error of errors.error.validationErrors) {
                    for (const field of error.fields) {
                        if (field.startsWith(`cdrBanking.discounts[${i}]`)) {
                            this.discountsErrors[i] = error.message;
                        }
                    }
                }
            }

        }
    }

}
