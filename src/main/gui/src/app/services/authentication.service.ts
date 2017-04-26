import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map'
import {environment} from '../../environments/environment';
/**
 * http://jasonwatmore.com/post/2016/08/16/angular-2-jwt-authentication-example-tutorial
 */
@Injectable()
export class AuthenticationService {
  /**
   *
   */
  public token: string;

  /**
   *
   * @param http
   */
  constructor(private http: Http) {
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
  }

  /**
   *
   * @param username
   * @param password
   * @returns {Observable<R>}
   */
  login(username, password): Observable<boolean> {
    return this.http.post(environment.apiUrl + '/api/1/user/login', JSON.stringify({ username: username, password: password }))
      .map((response: Response) => {
        console.log(response)
        // login successful if there's a jwt token in the response
        let token = response.json() && response.json().token;
        if (token) {
          // set token property
          this.token = token;

          // store username and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify({ username: username, token: token }));

          // return true to indicate successful login
          return true;
        } else {
          // return false to indicate failed login
          return false;
        }
      });
  }

  /**
   *
   */
  logout(): void {
    // clear token remove user from local storage to log user out
    this.token = null;
    localStorage.removeItem('currentUser');
  }
}
