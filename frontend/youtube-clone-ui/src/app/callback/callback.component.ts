import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent {

  constructor(private userService: UserService, private router: Router){
    console.log("callback component Called:");
    this.userService.registerUser();
    this.router.navigateByUrl('');  //when callback called it will redirect on Home Page
  }

}
