import { Component, OnInit } from '@angular/core';
import { DioProduct, FormFieldType, ProductAdminService } from '@bizaoss/deepthought-admin-angular-client';
import { map, switchMap } from 'rxjs/operators';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { LayoutService } from '@app/layout/layout.service';
import { DialogService } from 'primeng/api';
import { ProductCreateEditComponent } from '../../product-create-edit/product-create-edit.component';
import { TypeManagementService } from '@app/core/services/type-management.service';

@Component({
  selector: 'app-brand-view-products',
  templateUrl: './brand-view-products.component.html',
  styleUrls: ['./brand-view-products.component.scss']
})
export class BrandViewProductsComponent implements OnInit {

    brandId: string;
    products: DioProduct[];

    constructor(
        private route: ActivatedRoute,
        private layoutService: LayoutService,
        private dialogService: DialogService,
        private productsApi: ProductAdminService,
        private typeManager: TypeManagementService,
    ) { }

    ngOnInit() {
        this.layoutService.togglePageLoader.emit(true);

        this.route.parent.paramMap.pipe(
            map((params: ParamMap) => this.brandId = params.get('brandId')),
            switchMap(() => this.fetchProducts()),
        ).subscribe(() => this.layoutService.togglePageLoader.emit(false));
    }

    fetchProducts() {
        return this.productsApi.listProducts(this.brandId).pipe(
            map(this.onFetchProductsSuccess.bind(this))
        );
    }

    onFetchProductsSuccess(products) {
        this.products = products;
    }

    getProductCategory(category) {
        return this.typeManager.getLabel(FormFieldType.BANKINGPRODUCTCATEGORY, category);
    }

    createProduct() {
        const ref = this.dialogService.open(ProductCreateEditComponent, {
            header: 'Add product',
            width: '40%',
            style: {
                'overflow-y': 'auto',
                'overflow-x': 'hidden',
                'max-height': '80vh',
                'min-height': '250px'
            },
            data: { brandId: this.brandId }
        });

        ref.onClose.subscribe((product) => product ? this.fetchProducts().subscribe() : void(0));
    }
}
