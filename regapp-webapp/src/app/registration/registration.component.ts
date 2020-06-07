import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { User } from '../user.model'
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  error_message: String;
  userList: User[];
  preview: boolean;
  registrationForm: FormGroup;
  loginCredentials: FormGroup;
  user: User;
  previewUser: User;
  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string;
  imageName: any;
  admin = true;
  superadmin = false;
  loginForm: FormGroup;
  login_error_message: String;
  format=false;
  registrationType='';

  constructor(private httpClient: HttpClient, private registrationService: RegistrationService, private router: Router) { }



  ngOnInit() {
    this.preview = false;
    this.registrationForm = new FormGroup({
      username: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.email, Validators.required]),
      registrationType: new FormControl('self', [Validators.required]),
      numberOfParticipants: new FormControl(1, [Validators.required]),
      contact: new FormControl(null, [Validators.required]),
      image: new FormControl(null, [Validators.required]),
    })
    this.loginForm = new FormGroup({
      email: new FormControl(null,[Validators.email]),
      password: new FormControl()
    })

    this.registrationService.getAllUsers().subscribe(<User>(data) => {
      this.userList = data;
      for (let i = 0; i < this.userList.length; i++) {
        this.userList[i].image = 'data:image/jpeg;base64,' + this.userList[i].image;
      }
    })

  }

  backHome(){
    window.location.reload();
  }
  login() {

    if (this.loginForm.value.email == "superadmin" && this.loginForm.value.password == "superadmin") {
      this.superadmin = true;
      this.admin = false;
      this.loginForm.reset();
      return;
    }

    this.registrationService.login(this.loginForm.value.email, this.loginForm.value.password)
      .subscribe(data => {
        if (data == null) {
          this.login_error_message = "Invalid Credentials";
          setTimeout(() => {
            this.login_error_message = null;
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

  showLoginWindow(){
    document.getElementById("overlay").style.display = "block";
  }
  hideLoginWindow(){
    document.getElementById("overlay").style.display = "none";
  }


  registered() {
    this.registrationService.getUserStatus(this.registrationForm.value.email).subscribe(<Boolean>(data) => {
      if (data) {
        this.error_message = "User already registered"
        return;
      } else {
        this.error_message = null
      }
    })
  }
  onRegistration() {

    let formdata: FormData = new FormData();
    this.user = {
      numberOfTickets: this.registrationForm.value.numberOfParticipants,
      registrationType: this.registrationForm.value.registrationType,
      email: this.registrationForm.value.email,
      image: null,
      userName: this.registrationForm.value.username,
      contactNumber: this.registrationForm.value.contact
    }
    formdata.append('user', JSON.stringify(this.user));
    formdata.append('file', this.selectedFile);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    const options = ({
      headers: headers
    });
    console.log(this.registrationForm.value)
    this.registrationService.registerUser(this.user,this.selectedFile)
      .subscribe((response) => {
        this.preview = true;
        this.previewUser = response;
        this.base64Data = response.image;
        this.previewUser.image = 'data:image/jpeg;base64,' + this.base64Data;
        console.log(response);
      }
      );
  }
  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
    // console.log(this.selectedFile.name.split('.')[1]);
    let format = this.selectedFile.name.split('.')[1];
    if(!(format=="png" || format=="jpeg" || format=='jpg'))
    {
      this.error_message="please choose the correct image format";
      this.format=true;
    }

  }
}

