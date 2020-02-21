import { CdrFormGroup } from '@app/shared/forms/crd-form-group.class';

export interface IProductFormCreator<T> {

    createForm: (entity: T) => CdrFormGroup

}
