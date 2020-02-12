import { EventEmitter, Injectable } from '@angular/core';
import * as introJs from 'intro.js/intro';
import { timer } from 'rxjs';
import { map } from 'rxjs/operators';
import { Step } from 'intro.js';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

    togglePageLoader: EventEmitter<boolean> = new EventEmitter<boolean>();

    private introJS = introJs();

    constructor() { }

    runWalkthrough(steps?: Step[]) {
        const HIDE_WALKTHROUGH_KEY = 'hideWalkthrough';
        const hideWalkthrough = localStorage.getItem(HIDE_WALKTHROUGH_KEY);

        if (hideWalkthrough === 'true') {
            return;
        }

        timer(0).pipe(
            map(() => {
                this.introJS.setOptions({
                    showBullets: false,
                    showStepNumbers: false,
                    steps
                });

                this.introJS.start();
            }),
            map(() => {
                const arrowEl = document.querySelector('.introjs-arrow');

                const pEl = document.createElement('p');

                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.id = 'id';
                checkbox.style.marginRight = '5px';

                const label = document.createElement('label');
                label.htmlFor = 'id';
                label.appendChild(document.createTextNode('Do not show again'));

                pEl.appendChild(checkbox);
                pEl.appendChild(label);

                arrowEl.parentNode.insertBefore(pEl, arrowEl.nextSibling);

                this.introJS.onexit(() => checkbox.checked ? localStorage.setItem(HIDE_WALKTHROUGH_KEY, 'true') : void 0);
            })
        ).subscribe();
    }
}
