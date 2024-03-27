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

    if (localStorage.getItem('fname') && localStorage.getItem('token')) {
      req = req.clone({
        setHeaders: {
          "withCredentials": "true",
          "name": "Harsh",

          Authorization: this.http.getToken()
        }
      })
    }
    console.log(req.headers.get("Authorization") + "------------------->>>")
    
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
     
        if (error.status === 401) {
          localStorage.clear();
          this.router.navigate(['/login'], {queryParams: { errorMessage: error.error },} );         
         }

        if (error.status === 403) {
          localStorage.clear();
          this.router.navigate(['/login'],{ queryParams: { errorMessage: 'Your Session has been Expired! Please Re-Login'} }  );
         }

         
        return throwError(error);
      }
      )
    );







  }

}