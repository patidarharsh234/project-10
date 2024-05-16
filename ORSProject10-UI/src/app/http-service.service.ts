import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router'



@Injectable()

export class HttpServiceService {


  token = '';
  form = {
    message: '',
    error: false
  };


  userparams = {
    url: '',
    sessionExpiredMsg: '',
    methodType: '',
  };


  setToken(token) {
    this.token = localStorage.getItem('token');
    //  console.log(this.token + '----> inside setToken');
  }

  getToken() {
    console.log(localStorage.getItem('token') + '====>> getToken');
    return localStorage.getItem('token');
  }

  getJsessionid(){
    return localStorage.getItem('jsessionid');
  }

  constructor(private router: Router, private httpClient: HttpClient) {

  }
  isLogout() {
    console.log('url>>>------------------------->>>',this.router.url );

    let JSESSIONID = localStorage.getItem('fname');
    if ((JSESSIONID == "null" || JSESSIONID === null) && (this.router.url != "/login" && this.router.url != "/Auth" && this.router.url != "/logout"
      && this.router.url != "/forgotpassword"
      && this.router.url != "/signup"
      && this.router.url != "/login/true"
    )) {
      // this.form.message = "Your Session has been Expired! Please Re-Login";
      //  this.form.error = true;
     //  this.userparams.url = this.router.url;// to navigate the URI request.
      // this.router.navigateByUrl("/login");


      return true;
    } else {
      this.userparams.url ='';
      return false;
    }
  }



  get(endpoint, callback) {
    this.userparams.url = this.router.url;
   // console.log('httpservice return for auth class service-------1');//auth runig proccessing cheking 
    return this.httpClient.get(endpoint).subscribe((data) => {
    //  console.log('httpservice return for auth class service-------2');//auth runig proccessing cheking
      callback(data,)

    }


    
    
    
    
    );
  }

  post(endpoint, bean, callback) {
      this.userparams.url = this.router.url;
    return this.httpClient.post(endpoint, bean).subscribe(
      (data) => {
      callback(data);

    }


    
    );
  }


}


