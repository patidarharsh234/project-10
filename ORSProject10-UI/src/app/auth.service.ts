import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { HttpServiceService } from './http-service.service';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements HttpInterceptor {


  constructor(private http: HttpServiceService, private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    console.log('auth request-response');

    let modifiedRequest=req.clone({
      withCredentials: true
    })
    
    // const sessionId = sessionStorage.getItem("sessionId");
    if (localStorage.getItem('fname') && localStorage.getItem('token')) {
     
      modifiedRequest = modifiedRequest.clone({
        setHeaders: {
          "name": "Harsh",
          // 'sessionId':sessionId,
          Authorization: this.http.getToken(),
          jsessionid:this.http.getJsessionid()
        }
      })

    }
    console.log(req.headers.get("Authorization") + "------------------->>>")
    
    return next.handle( modifiedRequest).pipe(
      catchError((error: HttpErrorResponse) => {
     
        if (error.status === 401) {
          localStorage.clear();
          this.router.navigate(['/login'], {queryParams: { errorMessage: error.error },} );         
         }

        // if (error.status === 403) {
        //   localStorage.clear();
        //   this.router.navigate(['/login'],{ queryParams: { errorMessage: "Your Session has been Expired! Please Re-Login"} }  );
        //  }


         //--->FrontCtl se message lane ke tiem pr na ki jwtRequestFilter pr.
         if (error.status === 403) {
          localStorage.clear();
          this.router.navigate(['/login'],{ queryParams: { errorMessage: error.error} }  );
         }

         
        return throwError(error);
                                              }
      )
    );







  }

}