import { AbstractControl, FormGroup } from '@angular/forms';
import { CdrFormInput } from '@app/shared/forms/cdr-form-control/cdr-form-control.component';

export class CdrFormGroup extends FormGroup {

    private _submitted = false;

    hideAllControls() {
        Object.keys(this.controls).forEach((key) => (this.controls[key] as CdrFormInput).isVisible = false);
    }

    showControl(controlName: string): void {
        (this.controls[controlName] as CdrFormInput).isVisible = true;
    }

    get submitted() { return this._submitted; };

    setSubmitted(submitted) {
        this._submitted = submitted;
    }

    setServerErrors({ error: { type, validationErrors: errors } }) {
        if (type !== 'VALIDATION_ERROR') {
            return;
        }

        const fieldsMap = {};

        const mapFieldsToObject = (map: any, control: any, prefix: string) => {
            for (const key in control.controls) {
                const _prefix = !prefix ? key : (!control.controls.length ? `${prefix}.${key}` : `${prefix}[${key}]`);

                map[_prefix] = control.controls[key];

                if (control.controls[key].controls) {
                    mapFieldsToObject(map, control.controls[key], key);
                }
            }
        };

        mapFieldsToObject(fieldsMap, this, null);

        for (const error of errors) {
            for (const field of error.fields) {
                if (fieldsMap[field]) {
                    fieldsMap[field].setErrors({ SERVER: error.message });
                }
            }
        }
    }

}
