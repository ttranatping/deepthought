import { Component, OnInit } from '@angular/core';
import { AbstractControl, Validators } from '@angular/forms';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/api';
import { TypeManagementService } from '@app/core/services/type-management.service';
import {
    BankingProductFeeType,
    DioProductFee,
    DioSchemeType, FormFieldType,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { CdrFormInput, CdrFormSelect } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';
import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';

@Component({
  selector: 'app-product-fee-create-edit',
  templateUrl: './product-fee-create-edit.component.html',
  styleUrls: ['./product-fee-create-edit.component.scss']
})
export class ProductFeeCreateEditComponent implements OnInit {

    brandId: string;
    productId: string;

    feeForm = new CdrFormGroup({
        id:     new CdrFormInput(null, '', [Validators.required]),
        schemeType:   new CdrFormSelect(null, 'Scheme type', [Validators.required], []),
    });

    cdrBankingForm = new CdrFormGroup({
        feeType:            new CdrFormSelect(null, 'Type', [Validators.required]),
        name:               new CdrFormInput('', 'Name', []),
        amount:             new CdrFormInput('', 'Amount', []),
        balanceRate:        new CdrFormInput('', 'Balance rate', []),
        transactionRate:    new CdrFormInput('', 'Transaction rate', []),
        accruedRate:        new CdrFormInput('', 'Accrued rate', []),
        accrualFrequency:   new CdrFormInput('', 'Accrual frequency', []),
        currency:           new CdrFormInput('', 'Currency'),
        additionalValue:    new CdrFormInput(null, 'Additional value'),
        additionalInfo:     new CdrFormInput('', 'Additional info'),
        additionalInfoUri:  new CdrFormInput('', 'Additional info URI'),
    });

    fee: DioProductFee;

    constructor(
        private typeManager: TypeManagementService,
        private ref: DynamicDialogRef,
        private config: DynamicDialogConfig,
        private productsApi: ProductAdminService,
    ) { }

    ngOnInit() {
        this.feeForm.addControl('cdrBanking', this.cdrBankingForm);

        const idControl = this.feeForm.controls.id as CdrFormInput;
        idControl.isVisible = false;

        const schemeTypeControlOptions = Object.keys(DioSchemeType).map((key) => ({
            value: DioSchemeType[key],
            label: DioSchemeType[key],
        }));

        const schemeTypeControl = this.feeForm.controls.schemeType as CdrFormSelect;
        schemeTypeControl.options = schemeTypeControlOptions;
        schemeTypeControl.setValue(schemeTypeControlOptions[0].value);
        schemeTypeControl.disable();
        schemeTypeControl.isVisible = false;

        const feeTypeOptions = Object.keys(BankingProductFeeType).map((key) => ({
            value: BankingProductFeeType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTFEETYPE, BankingProductFeeType[key]),
        }));

        const feeTypeControl = this.cdrBankingForm.controls.feeType as CdrFormSelect;
        feeTypeControl.options = feeTypeOptions;
        feeTypeControl.setValue(feeTypeOptions[0].value);

        this.getConfigProp('brandId', true);
        this.getConfigProp('productId', true);
        this.getConfigProp('fee');

        if (this.fee) {
            this.fillForm(this.fee);
        } else {
            this.feeForm.removeControl('id');
        }
    }

    getConfigProp(propName, required = false) {
        if (this.config.data && this.config.data[propName]) {
            this[propName] = this.config.data[propName];
        } else if (required) {
            this.ref.close(null);
            throw new Error(`'${propName}' is required param`);
        }
    }

    fillForm(fee: DioProductFee) {
        this.feeForm.controls.id.setValue(fee.id);
        this.feeForm.controls.schemeType.setValue(fee.schemeType);

        this.cdrBankingForm.controls.feeType.setValue(fee.cdrBanking.feeType);
        this.cdrBankingForm.controls.name.setValue(fee.cdrBanking.name);
        this.cdrBankingForm.controls.amount.setValue(fee.cdrBanking.amount);
        this.cdrBankingForm.controls.balanceRate.setValue(fee.cdrBanking.balanceRate);
        this.cdrBankingForm.controls.transactionRate.setValue(fee.cdrBanking.transactionRate);
        this.cdrBankingForm.controls.accruedRate.setValue(fee.cdrBanking.accruedRate);
        this.cdrBankingForm.controls.accrualFrequency.setValue(fee.cdrBanking.accrualFrequency);
        this.cdrBankingForm.controls.currency.setValue(fee.cdrBanking.currency);
        this.cdrBankingForm.controls.additionalValue.setValue(fee.cdrBanking.additionalValue);
        this.cdrBankingForm.controls.additionalInfo.setValue(fee.cdrBanking.additionalInfo);
        this.cdrBankingForm.controls.additionalInfoUri.setValue(fee.cdrBanking.additionalInfoUri);
    }

    onCancel() {
        this.ref.close(null);
    }

    onSave() {
        this.feeForm.setSubmitted(true);

        if (!this.feeForm.valid) {
            return;
        }

        const saving$ = this.fee
            ? this.productsApi.updateProductFee(this.brandId, this.productId, this.fee.id, this.feeForm.getRawValue())
            : this.productsApi.createProductFee(this.brandId, this.productId, this.feeForm.getRawValue())
        ;

        saving$.subscribe(
            (fee) => this.ref.close(fee),
            this.onSavingError.bind(this)
        );

    }

    onSavingError({ error: { type, validationErrors: errors } }) {
        if (type === 'VALIDATION_ERROR') {

            const mapErrorFieldControl: { [key: string]: AbstractControl } = {
                'cdrBanking.name':              this.cdrBankingForm.get('name'),
                'cdrBanking.amount':            this.cdrBankingForm.get('amount'),
                'cdrBanking.balanceRate':       this.cdrBankingForm.get('balanceRate'),
                'cdrBanking.transactionRate':   this.cdrBankingForm.get('transactionRate'),
                'cdrBanking.accruedRate':       this.cdrBankingForm.get('accruedRate'),
                'cdrBanking.accrualFrequency':  this.cdrBankingForm.get('accrualFrequency'),
                'cdrBanking.currency':          this.cdrBankingForm.get('currency'),
                'cdrBanking.additionalValue':   this.cdrBankingForm.get('additionalValue'),
                'cdrBanking.value':             this.cdrBankingForm.get('additionalValue'),
                'cdrBanking.additionalInfo':    this.cdrBankingForm.get('additionalInfo'),
                'cdrBanking.additionalInfoUri': this.cdrBankingForm.get('additionalInfoUri'),
            };

            for (const error of errors) {
                for (const field of error.fields) {
                    if (mapErrorFieldControl[field]) {
                        mapErrorFieldControl[field].setErrors({ SERVER: error.message });
                    }
                }
            }
        }
    }

}
