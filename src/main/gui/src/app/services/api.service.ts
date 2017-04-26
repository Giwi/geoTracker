import {Injectable} from "@angular/core";
import {Response, Headers, RequestOptions}  from '@angular/http';
import {Router} from '@angular/router';
import 'rxjs/add/operator/map';

import {ToasterService} from "angular2-toaster";
import {AuthenticationService} from "./authentication.service";

/**
 * ApiService
 */
@Injectable()
export class ApiService {
  protected toasterService: ToasterService;
  protected authenticationService: AuthenticationService;
  protected router: Router;

  /**
   *
   * @param toasterService
   * @param authenticationService
   * @param router
   */
  constructor(toasterService: ToasterService,
              authenticationService: AuthenticationService, router: Router, ) {
    this.toasterService = toasterService;
    this.authenticationService = authenticationService;
    this.router = router;
  }

  /**
   *
   * @param res
   * @returns {any}
   */
  extractData(res: Response): any {
    return res.json();
  }

  /**
   *
   * @param error
   * @returns {string}
   */
  handleError(error: any): String {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    if(error.status === 401) {
      this.authenticationService.logout();
      this.router.navigate(['/login']);
    }
    let errMsg = (error.message) ? error.message : error.status ? error.statusText : 'Server error';
    console.error(error); // log to console instead
    if (error._body && JSON.parse(error._body)) {
      console.log(JSON.parse(error._body));
    }
    this.toasterService.pop('error', 'Une erreur s\'est produite', errMsg);
    return errMsg;
  }

  /**
   *
   * @returns {RequestOptions}
   */
  addHeaderToken(): RequestOptions {
    let headers = new Headers({ 'Authorization': 'Bearer ' + this.authenticationService.token });
    return new RequestOptions({headers: headers});
  }
}
