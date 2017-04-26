import {Component, OnInit} from "@angular/core";
import {UserService} from "../../../services/user.service";
import {User} from "../../../model/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {UserAddComponent} from "../user-add/user-add.component";

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  entryComponents: [UserAddComponent]
})
export class UserListComponent implements OnInit {

  users: User[];

  constructor(private userService: UserService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getParam();
  }


  open() {
    const modalRef = this.modalService.open(UserAddComponent);
    modalRef.result.then(r => {
      modalRef.close('');
      this.getParam();
    });
    // modalRef.componentInstance.user = new User();
  }

  private getParam() {
    this.userService.getUserList().subscribe(res => {
      this.users = res;
    }, err => this.userService.handleError(err));
  }
}
