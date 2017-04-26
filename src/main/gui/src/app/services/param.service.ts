import {Injectable} from '@angular/core';
import {ApiService} from "./api.service";
import {Router} from "@angular/router";
import {Http, Response} from "@angular/http";
import {AuthenticationService} from "./authentication.service";
import {ToasterService} from "angular2-toaster";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
/**
 *
 */
@Injectable()
export class ParamService extends ApiService {

  /**
   *
   * @param router
   * @param http
   * @param authenticationService
   * @param toasterService
   */
  constructor(router: Router, private http: Http, authenticationService: AuthenticationService, toasterService: ToasterService) {
    super(toasterService, authenticationService, router);
  }

  /**
   *
   * @returns {Observable<any>}
   */
  getRoles(): Observable<any[]> {
    return this.http.get(environment.apiUrl + '/api/1/private/param/roles', this.addHeaderToken())
      .map((response: Response) => {
        console.log(response);
        return this.extractData(response);
      });
  }
}
