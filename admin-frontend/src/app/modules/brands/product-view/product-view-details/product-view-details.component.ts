import { Component, OnInit } from '@angular/core';
import { DioProduct, DioProductBundle, FormFieldType, ProductAdminService } from '@bizaoss/deepthought-admin-angular-client';
import { DateFormatPipe } from '@app/shared/pipes/date-format.pipe';
import { TypeManagementService } from '@app/core/services/type-management.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { map } from 'rxjs/operators';

interface IDetailsProp {
    key: string;
    label: string;
}

@Component({
    selector: 'app-product-view-details',
    templateUrl: './product-view-details.component.html',
    styleUrls: ['./product-view-details.component.scss'],
    providers: [DateFormatPipe]
})
export class ProductViewDetailsComponent implements OnInit {

    brandId: string;
    productId: string;

    product: DioProduct;

    summaryDetails: IDetailsProp[] = [
        { key: 'id', label: 'Product ID' },
        { key: 'name', label: 'Name' },
        { key: 'description', label: 'Description' },
        { key: 'cdrBanking.productCategory', label: 'Product Category' },
        { key: 'cdrBanking.isTailored', label: 'Tailored Product' },
        { key: 'cdrBanking.effective', label: 'Effective Date' },
        { key: 'cdrBanking.lastUpdated', label: 'Last Updated' },
    ];

    bundles: DioProductBundle[];

    constructor(
        private route: ActivatedRoute,
        private dateFormatPipe: DateFormatPipe,
        private typeManager: TypeManagementService,
        private productAdminApi: ProductAdminService
    ) { }

    ngOnInit() {
        this.route.parent.paramMap.subscribe((params: ParamMap) => {
            this.brandId = params.get('brandId');
            this.productId = params.get('productId');

            this.fetchBundles().subscribe();
        });
    }

    fetchBundles() {
        return this.productAdminApi.listBundlesForProduct(this.brandId, this.productId).pipe(
            map((bundles) => this.bundles = bundles)
        );
    }

    setProduct(product: DioProduct) {
        this.product = product;
    }

    getEffectiveDate(from, to) {
        let effectiveDate = `${ from ? this.dateFormatPipe.transform(from) : '-' }`;

        if (from) {
            effectiveDate += ` - ${ to && !to.startsWith('9999') ? this.dateFormatPipe.transform(to) : 'Present' }`;
        }

        return effectiveDate;
    }

    getProductCategory(category) {
        return this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTCATEGORY, category);
    }

    addBundle() {}

    removeBundle(bundle: DioProductBundle) {}

}
