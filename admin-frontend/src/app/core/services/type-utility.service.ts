import {Injectable} from '@angular/core';
import {CurrencyPipe, PercentPipe} from '@angular/common';
import { SelectItem } from 'primeng/api';
import {
    BankingProductCategory,
    BankingProductConstraintType,
    BankingProductDepositRateType,
    BankingProductDiscountEligibilityType,
    BankingProductDiscountType,
    BankingProductEligibilityType,
    BankingProductFeatureType,
    BankingProductFeeType,
    BankingProductLendingRateType,
    DioSchemeType,
    FormFieldType
} from '@bizaoss/deepthought-admin-angular-client';
import { TypeManagementService } from '@app/core/services/type-management.service';
import { _ } from 'underscore';
import * as moment from 'moment';


@Injectable({
    providedIn: 'root'
})
export class TypeUtilityService {

    constructor(
        private percentPipe: PercentPipe,
        private currencyPipe: CurrencyPipe,
        private typeManager: TypeManagementService
    ) {}

    convertDuration(period: string) {
        if (period === null || period === '') {
            return 'N/A';
        }

        if (period.match(/^P(\d+)D$/)) {
            const days = period.match(/^P(\d+)D$/)[1];
            return days === '1' ? '1 Day' : days + ' days';
        } else if (period.match(/^P(\d+)M$/)) {
            const months = period.match(/^P(\d+)M$/)[1];
            return months === '1' ? '1 Month' : months + ' months';
        } else if (period.match(/^P(\d+)Y$/)) {
            const years = period.match(/^P(\d+)Y$/)[1];
            return years === '1' ? '1 Year' : years + ' years';
        }

        return moment.duration(period).humanize(false);
    }

    convertRateString(rateString: any) {
        return this.percentPipe.transform(rateString, '1.2-4');
    }

    getUniqueKeyList(keyName: string, objectList) {
        return _.chain(objectList).map(item => item[keyName]).uniq().value();
    }

    convertValueString(valueString: string) {
        return this.currencyPipe.transform(valueString);
    }

    getSchemeTypeOptions(): SelectItem[] {
        return Object.keys(DioSchemeType).map((key) => ({
            value: DioSchemeType[key],
            label: DioSchemeType[key]
        }));
    }

    getProductCategoryOptions(): SelectItem[] {
        return Object.keys(BankingProductCategory).map((key) => ({
            value: BankingProductCategory[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTCATEGORY, BankingProductCategory[key])
        }));
    }

    getEligibilityTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductEligibilityType).map((key) => ({
            value: BankingProductEligibilityType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTELIGIBILITYTYPE, BankingProductEligibilityType[key]),
        }));
    }

    getConstraintTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductConstraintType).map((key) => ({
            value: BankingProductConstraintType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTCONSTRAINTTYPE, BankingProductConstraintType[key]),
        }));
    }

    getFeatureTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductFeatureType).map((key) => ({
            value: BankingProductFeatureType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTFEATURETYPE, BankingProductFeatureType[key]),
        }));
    }

    getLendingRateTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductLendingRateType).map((key) => ({
            value: BankingProductLendingRateType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTLENDINGRATETYPE, BankingProductLendingRateType[key]),
        }));
    }

    getDepositRateTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductDepositRateType).map((key) => ({
            value: BankingProductDepositRateType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTDEPOSITRATETYPE, BankingProductDepositRateType[key]),
        }));
    }

    getFeeTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductFeeType).map((key) => ({
            value: BankingProductFeeType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTFEETYPE, BankingProductFeeType[key]),
        }));
    }

    getDiscountTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductDiscountType).map((key) => ({
            value: BankingProductDiscountType[key],
            label: this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTDISCOUNTTYPE, BankingProductDiscountType[key]),
        }));
    }

    getDiscountEligibilityTypeOptions(): SelectItem[] {
        return Object.keys(BankingProductDiscountEligibilityType).map((key) => ({
            value: BankingProductDiscountEligibilityType[key],
            label: this.typeManager.getLabel(
                FormFieldType.BANKINGPRODUCTDISCOUNTELIGIBILITYTYPE,
                BankingProductDiscountEligibilityType[key]
            ),
        }));
    }
}
