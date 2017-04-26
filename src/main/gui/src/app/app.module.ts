import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {UserAddComponent} from "./components/admin/user-add/user-add.component";
import {LoginBoxComponent} from "./components/login-box/login-box.component";
import {MainPageComponent} from "./components/main-page/main-page.component";
import {SignupComponent} from "./components/signup/signup.component";
import {MainAdminComponent} from "./components/admin/main-admin/main-admin.component";
import {UserListComponent} from "./components/admin/user-list/user-list.component";
import {ToasterModule} from "angular2-toaster";
import {AuthGuard} from "./guards/auth.guard";
import {RouterModule} from "@angular/router";
import {ParamService} from "./services/param.service";
import {EventsService} from "./services/event.service";
import {AuthenticationService} from "./services/authentication.service";
import {UserService} from "./services/user.service";
import {ApiService} from "./services/api.service";
import {AppComponent} from "./components/app.component";

@NgModule({
    declarations: [
        AppComponent,
        LoginBoxComponent,
        MainPageComponent,
        SignupComponent,
        MainAdminComponent,
        UserListComponent,
        UserAddComponent
    ],
    imports: [
        NgbModule.forRoot(),
        RouterModule.forRoot([
            {
                path: '', component: MainPageComponent, pathMatch: 'full'
            },
            {
                path: 'login', component: LoginBoxComponent
            },
            {
                path: 'signup', component: SignupComponent
            },
            {
                path: 'admin', component: MainAdminComponent, canActivate: [AuthGuard]
            }
        ], {useHash: true}),
        BrowserModule,
        FormsModule,
        HttpModule,
        ToasterModule
    ],
    providers: [
        ApiService,
        UserService,
        AuthenticationService,
        AuthGuard,
        EventsService,
        ParamService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
