import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private httpClient: HttpClient) { }

  private userId: string = '';

  subscribeToUser(userId: string): Observable<boolean>{
    console.log("passed button click subscribe in service");
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/subscribe/"+ userId, null);
  }

  unSubscribeToUser(userId: string): Observable<boolean>{
    return this.httpClient.post<boolean>("http://localhost:8080/api/user/unSubscribe/"+ userId, null);
  }

  registerUser() {
    console.log("before register in bg");
     this.httpClient.get("http://localhost:8080/api/user/register", {responseType: "text"})
     .subscribe(data=>{
      this.userId = data;
      console.log("userdetails", data);
     });
  }

  getUserId(): string {  //return type string
    return this.userId;
  }
}
