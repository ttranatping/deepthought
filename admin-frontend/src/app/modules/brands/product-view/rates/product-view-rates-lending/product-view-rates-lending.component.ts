import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { TypeManagementService } from '@app/core/services/type-management.service';
import { TypeUtilityService } from '@app/core/services/type-utility.service';
import {
    BankingProductLendingRateType,
    DioProductRateDeposit,
    DioProductRateLending,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';
import { ConfirmationService, DialogService } from 'primeng/api';
import { ProductRateLendingCreateEditComponent } from './product-rate-lending-create-edit/product-rate-lending-create-edit.component';

@Component({
    selector: 'app-product-view-rates-lending',
    templateUrl: './product-view-rates-lending.component.html',
    styleUrls: ['./../product-view-rates.component.scss']
})
export class ProductViewRatesLendingComponent implements OnInit {

    brandId: string;
    productId: string;

    fixedRates: DioProductRateLending[];
    notFixedRates: DioProductRateLending[];

    constructor(
        private route: ActivatedRoute,
        private typeManager: TypeManagementService,
        private typeUtility: TypeUtilityService,
        private productsApi: ProductAdminService,
        private confirmationService: ConfirmationService,
        private dialogService: DialogService,
    ) { }

    ngOnInit() {
        this.route.parent.paramMap.subscribe((params: ParamMap) => {
            this.brandId = params.get('brandId');
            this.productId = params.get('productId');

            this.fetchRates();
        });
    }

    fetchRates() {
        this.productsApi.listProductRateLendings(this.brandId, this.productId).subscribe((rates) => {

            const groupedRates = rates.reduce((acc, rate) => {
                if (rate.cdrBanking.lendingRateType === BankingProductLendingRateType.FIXED) {
                    acc.fixed.push(rate);
                    return acc;
                }

                acc.notFixed.push(rate);
                return acc;
            }, { fixed: [], notFixed: [] });

            this.fixedRates = groupedRates.fixed;
            this.notFixedRates = groupedRates.notFixed;

        });
    }

    getLendingDetail(lendingRate: DioProductRateLending, fieldName: string) {

        switch (fieldName) {
            case 'NAME': return this.typeManager.getLabel('BANKING_PRODUCT_LENDING_RATE_TYPE', lendingRate.cdrBanking.lendingRateType);
            case 'DETAIL': return `${this.typeUtility.convertRateString(lendingRate.cdrBanking.rate)} per annum.`;
            case 'INFO':
                const detail = [];

                if (lendingRate.cdrBanking.calculationFrequency != null) {
                    detail.push(`Calculated every ${this.typeUtility.convertDuration(lendingRate.cdrBanking.calculationFrequency)} from account balance.`);
                }

                if (lendingRate.cdrBanking.applicationFrequency != null) {
                    detail.push(`Applied to account every ${this.typeUtility.convertDuration(lendingRate.cdrBanking.applicationFrequency)}.`);
                }

                if (lendingRate.cdrBanking.additionalInfo) {
                    detail.push(lendingRate.cdrBanking.additionalInfo);
                }

                if (lendingRate.cdrBanking.interestPaymentDue != null) {
                    detail.push(this.typeManager.getLabel('BANKING_PRODUCT_LENDING_RATE_INTEREST_PAYMENT_DUE', lendingRate.cdrBanking.interestPaymentDue));
                }

                if (lendingRate.cdrBanking.lendingRateType === BankingProductLendingRateType.DISCOUNT
                    || lendingRate.cdrBanking.lendingRateType === BankingProductLendingRateType.PENALTY
                    || lendingRate.cdrBanking.lendingRateType === BankingProductLendingRateType.FLOATING
                    || lendingRate.cdrBanking.lendingRateType === BankingProductLendingRateType.MARKETLINKED
                ) {
                    detail.push(lendingRate.cdrBanking.additionalValue);
                }

                if (lendingRate.cdrBanking.lendingRateType === BankingProductLendingRateType.BUNDLEDISCOUNTFIXED
                    || lendingRate.cdrBanking.lendingRateType === BankingProductLendingRateType.BUNDLEDISCOUNTVARIABLE
                ) {
                    detail.push(`Available when purchased as part of the ${lendingRate.cdrBanking.additionalValue} bundle`);
                }

                return detail.join(' ');
            default: return '';
        }
    }

    createRate() {
        const ref = this.dialogService.open(ProductRateLendingCreateEditComponent, {
            header: 'Create rate',
            width: '70%',
            style: {
                'overflow-y': 'auto',
                'overflow-x': 'hidden',
                'max-height': '80vh',
                'min-height': '250px'
            },
            data: { brandId: this.brandId, productId: this.productId }
        });

        ref.onClose.subscribe((newRate) => newRate ? this.fetchRates() : void(0));
    }

    editRate(depositRate: DioProductRateDeposit) {
        const ref = this.dialogService.open(ProductRateLendingCreateEditComponent, {
            header: 'Edit rate',
            width: '70%',
            style: {
                'overflow-y': 'auto',
                'overflow-x': 'hidden',
                'max-height': '80vh',
                'min-height': '250px'
            },
            data: { brandId: this.brandId, productId: this.productId, rate: depositRate }
        });

        ref.onClose.subscribe((editedRate) => editedRate ? this.fetchRates() : void(0));
    }

    removeRate(depositRate: DioProductRateDeposit) {
        this.confirmationService.confirm({
            message: `Are you sure want to remove rate?`,
            header: 'Remove rate',
            icon: null,
            accept: () => {
                this.productsApi
                    .deleteProductRateLending(this.brandId, this.productId, depositRate.id)
                    .subscribe(() => this.fetchRates());
            },
            reject: () => {}
        });
    }

}
