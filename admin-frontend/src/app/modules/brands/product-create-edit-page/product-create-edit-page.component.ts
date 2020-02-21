import { Component, OnInit } from '@angular/core';
import {
    DioProduct,
    ProductAdminService
} from '@bizaoss/deepthought-admin-angular-client';

import { ActivatedRoute, Router } from '@angular/router';
import { ProductBasicDetailsFormService } from '@app/core/services/product-forms/product-basic-details-form.service';
import { ProductCardArtsFormService } from '@app/core/services/product-forms/product-card-arts-form.service';
import { ProductFeatureFormCreatorService } from '@app/core/services/product-forms/product-feature-form-creator.service';
import { ProductEligibilitysFormService } from '@app/core/services/product-forms/product-eligibilitys-form.service';
import { ProductConstraintFormService } from '@app/core/services/product-forms/product-constraint-form.service';

@Component({
    selector: 'app-product-create-edit-page',
    templateUrl: './product-create-edit-page.component.html',
    styleUrls: ['./product-create-edit-page.component.scss'],
})
export class ProductCreateEditPageComponent implements OnInit {

    BASIC_DETAILS_INDEX = 0;
    CARD_ARTS_INDEX = 1;
    FEATURES_INDEX = 2;
    ELIGIBILITY_CONSTRAINTS_INDEX = 3;
    FEES_INDEX = 4;
    LENDING_RATES_INDEX = 5;
    DEPOSIT_RATES_INDEX = 6;

    isSubmitted = false;

    activeStepIndex = 0;

    steps: Array<{ label: string; }> = [
        { label: 'Basic Information' },
        { label: 'Card Arts' },
        { label: 'Features' },
        { label: 'Eligibility & Constraints' },
        { label: 'Fees' },
        { label: 'Lending Rates' },
        { label: 'Deposit Rates' },
    ];

    brandId: string;
    productId: string;

    product: DioProduct;

    forms = {
        basicDetails: this.productBasicDetailsForm.createForm(),
        cardArts: [],
        features: [],
        eligibilitys: [],
        constraints: []
    };

    get isNew() { return !this.productId; }

    constructor(
        private productBasicDetailsForm: ProductBasicDetailsFormService,
        private productCardArtsForm: ProductCardArtsFormService,
        private productFeatureFormCreator: ProductFeatureFormCreatorService,
        private productConstraintFormCreator: ProductConstraintFormService,
        private productEligibilitysFormCreator: ProductEligibilitysFormService,
        private route: ActivatedRoute,
        private router: Router,
        private productsApi: ProductAdminService,
    ) { }

    ngOnInit() {

        const params = this.route.snapshot.paramMap;

        this.brandId = params.get('brandId');
        this.productId = params.get('productId');

        // TODO: fetch product data --> fillForm
    }

    prevStep() {
        if (this.activeStepIndex === 0) {
            return;
        }

        this.activeStepIndex = this.activeStepIndex - 1;
    }

    nextStep() {
        if (this.activeStepIndex === this.steps.length - 1) {
            return;
        }

        this.activeStepIndex = this.activeStepIndex + 1;
    }

    goBack(productId: string = null) {
        !productId
            ? this.router.navigate(['/brands', this.brandId, 'products'])
            : this.router.navigate(['/brands', this.brandId, 'products', productId]);
    }

    addCardArt() {
        this.forms.cardArts.push(this.productCardArtsForm.createForm());
    }

    addFeature() {
        this.forms.features.push(this.productFeatureFormCreator.createForm());
    }

    addConstraint() {
        this.forms.constraints.push(this.productConstraintFormCreator.createForm());
    }

    addEligibility() {
        this.forms.eligibilitys.push(this.productEligibilitysFormCreator.createForm());
    }

    onSave() {
        this.isSubmitted = true;

        if (!this.forms.basicDetails.valid) {
            return;
        }

        const saving$ = this.product
            ? this.productsApi.updateProduct(this.brandId, this.productId, this.forms.basicDetails.value)
            : this.productsApi.createProduct(this.brandId, this.forms.basicDetails.value);

        saving$.subscribe(
            (product) => this.goBack(product.id),
            (errors) => this.forms.basicDetails.setServerErrors(errors)
        );
    }

}
