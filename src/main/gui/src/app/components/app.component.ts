import {Component, OnInit} from "@angular/core";
import {EventsService} from "../services/event.service";
import {User} from "../model/user";
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";
import {AuthenticationService} from "../services/authentication.service";
/**
 *
 */
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})

/**
 *
 */
export class AppComponent implements OnInit {
    logged = false;
    user: User;
    isCollapsed = true;

    /**
     *
     * @param eventsService
     * @param userService
     * @param router
     * @param authenticationService
     */
    constructor(private eventsService: EventsService, private userService: UserService, private router: Router,
                private authenticationService: AuthenticationService) {
    }

    /**
     *
     */
    ngOnInit() {
        this.eventsService.on('user-logged', user => {
            console.log('AppComponent', user);
            this.user = user;
            this.logged = true;
        });
        if (this.authenticationService.token) {
            this.userService.getCurrentUser().subscribe(result => {
                this.eventsService.broadcast('user-logged', result);
                this.router.navigate([this.router.url]);
            }, error => this.userService.handleError(error));
        }
    }

    /**
     *
     */
    logout() {
        this.isCollapsed = true;
        this.userService.logout();
        this.logged = false;
    }
}
