import { HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { LoginResponse, OidcSecurityService } from 'angular-auth-oidc-client';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'youtube-clone-ui';

  constructor(private oidcSecurityService: OidcSecurityService) {}
  
  ngOnInit() {
    this.oidcSecurityService.checkAuth().subscribe((loginResponse: LoginResponse) => {
      const { isAuthenticated, userData, accessToken, idToken} = loginResponse;
      console.log('app is authenticated', isAuthenticated);
      console.log("userData",userData);
      console.log("accessToken",accessToken);
      console.log("idToken",idToken);
       

    });
  
  }

}
