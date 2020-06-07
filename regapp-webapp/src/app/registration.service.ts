import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  adminId=-1; //0 for superadmin

  constructor(private httpClient:HttpClient) {

   }

   getAllUsers():Observable<User[]>{
    return this.httpClient.get<User[]>('http://localhost:1022/user/');
   }

   getUserStatus(email):Observable<Boolean>{
     return this.httpClient.get<Boolean>(`http://localhost:1022/user/registered/${email}`);
   }

   registerUser(user:User, selectedFile):Observable<User>{
    let formdata: FormData = new FormData();

    formdata.append('user', JSON.stringify(user));
    formdata.append('file', selectedFile);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');
    const options = ({
      headers: headers
    });
    return this.httpClient.post<User>('http://localhost:1022/user/', formdata, options);
   }

   login(email,password):Observable<any>{
    return this.httpClient.post(`http://localhost:1022/admin/login/${email}`,password);
   }

   signup(email,password):Observable<any>{
    return this.httpClient.post(`http://localhost:1022/admin/${email}`,password);
   }

   approveUser(email):Observable<Boolean>{
     return this.httpClient.put<Boolean>(`http://localhost:1022/admin/approve/${email}`,null);
   }
}
