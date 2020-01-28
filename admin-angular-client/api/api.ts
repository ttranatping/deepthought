export * from './brandAdmin.service';
import { BrandAdminService } from './brandAdmin.service';
export * from './bundleAdmin.service';
import { BundleAdminService } from './bundleAdmin.service';
export * from './productAdmin.service';
import { ProductAdminService } from './productAdmin.service';
export * from './type.service';
import { TypeService } from './type.service';
export const APIS = [BrandAdminService, BundleAdminService, ProductAdminService, TypeService];
