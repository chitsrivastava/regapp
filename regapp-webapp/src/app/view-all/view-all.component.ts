import { Component, OnInit } from '@angular/core';
import { User } from '../user.model';
import { HttpClient } from '@angular/common/http';
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-all',
  templateUrl: './view-all.component.html',
  styleUrls: ['./view-all.component.css']
})
export class ViewAllComponent implements OnInit {

  constructor(private httpClient: HttpClient, private registrationService:RegistrationService,private router:Router) { }
  userList:User[];

  ngOnInit() {

    if(this.registrationService.adminId<=0){
this.router.navigate([''])
    }

    this.registrationService.getAllUsers().subscribe(<User>(data)=>{
      this.userList = data;
      for(let i=0;i<this.userList.length;i++){
        this.userList[i].image = 'data:image/jpeg;base64,'+this.userList[i].image;
      }
    })
  }

  approve(email:String){
    this.registrationService.approveUser(email).subscribe(<Boolean>(data)=>{
      if(data){
          this.ngOnInit();
      }
      
    })
  }

}
