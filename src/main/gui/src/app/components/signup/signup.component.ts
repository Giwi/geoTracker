import {Component} from "@angular/core";
import {ApiService} from "../../services/api.service";
import {User} from "../../model/user";
import {ToasterService} from "angular2-toaster";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";

/**
 *
 */
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  providers: [ApiService],
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  user = new User();

  /**
   *
   * @param router
   * @param userService
   * @param toasterService
   */
  constructor(private router: Router, private userService: UserService, private toasterService: ToasterService) {
  }

  /**
   *
   */
  register() {
    this.userService.register(this.user).subscribe(() => {
      this.toasterService.pop('success', 'Inscription', 'Votre compte a été créé');
      this.user = new User();
      this.router.navigate(['/login']);
    }, error => this.userService.handleError(error));
  }

}
