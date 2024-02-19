import { NgModule } from '@angular/core';
import { AuthModule, LogLevel } from 'angular-auth-oidc-client';


@NgModule({
    imports: [AuthModule.forRoot({
        config: {
            authority: 'https://dev-8j5jaeo24js5nsvi.us.auth0.com',
            redirectUrl: window.location.origin,
            clientId: 'VKsDsuugMR0FGC0O2b0CiwSuZA91YuMh',
            scope: 'openid profile offline_access',
            responseType: 'code',
            silentRenew: true,
            useRefreshToken: true,
            logLevel: LogLevel.Debug,
            secureRoutes:['http://localhost:8080/'], //securing the backend 
            customParamsAuthRequest:{
                audience: 'http://localhost:8080'
            }
        }
      })],
    exports: [AuthModule],
})
export class AuthConfigModule {}
