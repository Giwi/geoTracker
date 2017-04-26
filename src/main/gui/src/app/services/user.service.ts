import {Injectable} from "@angular/core";
import {Http, Response}  from '@angular/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import 'rxjs/add/operator/map';

import {User} from '../model/user';
import {AuthenticationService} from "./authentication.service";
import {ApiService} from "./api.service";
import {ToasterService} from "angular2-toaster";
import {Router} from "@angular/router";

/**
 *
 */
@Injectable()
export class UserService extends ApiService {

  /**
   *
   * @param router
   * @param http
   * @param authenticationService
   * @param toasterService
   */
  constructor(router: Router, private http: Http, authenticationService: AuthenticationService,  toasterService: ToasterService) {
    super(toasterService, authenticationService, router);
  }

  /**
   *
   * @param user
   * @returns {Observable<boolean>}
   */
  login(user: User): Observable<boolean> {
    return this.authenticationService.login(user.username, user.password);
  }

  /**
   *
   */
  logout() {
    return this.authenticationService.logout();
  }

  /**
   *
   * @param user
   * @returns {Observable<R>}
   */
  register(user: User): any {
    return this.http.put(environment.apiUrl + '/api/1/user/register', {
      firstname: user.firstname,
      name: user.name,
      username: user.username,
      password: user.password
    })
      .map((response: Response) => {
        return super.extractData(response);
      });
  }

  /**
   *
   * @returns {Observable<R>}
   */
  getCurrentUser(): any {
    return this.http.get(environment.apiUrl + '/api/1/private/user', super.addHeaderToken())
      .map((response: Response) => {
        return super.extractData(response);
      });
  }

  getUserList() {
    return this.http.get(environment.apiUrl + '/api/1/private/user/all', super.addHeaderToken())
      .map((response: Response) => {
        return super.extractData(response);
      });
  }
}
