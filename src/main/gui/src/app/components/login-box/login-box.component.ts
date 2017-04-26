import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {User} from '../../model/user';
import {ToasterService} from "angular2-toaster";
import {EventsService} from "../../services/event.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-login-box',
  templateUrl: './login-box.component.html',
  providers: [ApiService],
  styleUrls: ['./login-box.component.css']
})
export class LoginBoxComponent {
  user = new User();

  constructor(private router: Router, private userService: UserService, private  toasterService: ToasterService, private eventsService: EventsService) {
  }

  login() {
    this.userService.login(this.user).subscribe(result => {
      if (result === true) {
        this.userService.getCurrentUser().subscribe(result => {
          this.toasterService.pop('success', 'Login', 'Login ok');
          this.eventsService.broadcast('user-logged', result);
          this.router.navigate(['/baab']);
        }, error => this.userService.handleError(error));
      } else {
        // login failed
        this.toasterService.pop('error', 'Une erreur s\'est produite', 'Mauvais couple login/mot de passe');
      }
    }, error => this.userService.handleError(error));
  }
}
