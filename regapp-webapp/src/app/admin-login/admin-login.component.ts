import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';
import { timer } from 'rxjs';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  constructor(private httpClient: HttpClient, private registrationService: RegistrationService, private router: Router) { }
  admin = true;
  message:String;
  superadmin = false;
  loginForm: FormGroup;
  error_message: String;
  ngOnInit() {
    this.loginForm = new FormGroup({
      email: new FormControl(null,[Validators.email]),
      password: new FormControl()
    })
  }

  login() {

    console.log("\n\n\njdyddtydjudtd",this.loginForm.value.email,this.loginForm.value.password)
    if (this.loginForm.value.email === "superadmin@sh" && this.loginForm.value.password === "123") {
      this.superadmin = true;
      this.admin = false;
      this.loginForm.reset();
      return;
    }

    this.registrationService.login(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(data => {
        if (data == null) {
          this.error_message = "Invalid Credentials";
          setTimeout(() => {
            this.error_message = null;
          }, 2000);

          return;
        }
        if (data['adminId'] != null) {
          this.registrationService.adminId = data['adminId'];
          this.router.navigate(['/admin'])
        }
        console.log(data)
      })
  }
  signup() {
    this.registrationService.signup(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(data => {
        if (data == null) {
          this.error_message = "Admin Already Exists";
          setTimeout(() => {
            this.error_message = null;
          }, 2000);
        }
        else{
          this.message="Success";
          setTimeout(()=>{this.message=null;
        this.loginForm.reset()},2000)
        }
      })
  }

}
