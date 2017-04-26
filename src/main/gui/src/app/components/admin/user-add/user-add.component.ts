import {Component, Input, OnInit} from "@angular/core";
import {User} from "../../../model/user";
import {ToasterService} from "angular2-toaster";
import {UserService} from "../../../services/user.service";
import {ParamService} from "../../../services/param.service";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
    selector: 'user-add',
    templateUrl: './user-add.component.html',
    styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {
    @Input()
    user: User;
    permissions: any[];

    /**
     *
     * @param activeModal
     * @param userService
     * @param toasterService
     * @param paramService
     */
    constructor(public activeModal: NgbActiveModal, private userService: UserService, private toasterService: ToasterService, private paramService: ParamService) {
    }

    /**
     *
     */
    register() {
        this.userService.register(this.user).subscribe(() => {
            this.toasterService.pop('success', 'Création', 'Ce compte a été créé');
            this.activeModal.close();
        }, error => this.userService.handleError(error));
    }

    /**
     *
     */
    ngOnInit() {
        if (!this.user) {
            this.user = new User();
            this.user.permissions.push('user');
        }
        this.paramService.getRoles().subscribe(res => {
            console.log(res)
            this.permissions = res;
        }, err => {
            this.paramService.handleError(err)
        });
    }
}
