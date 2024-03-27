import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { HttpServiceService } from '../http-service.service';
import { Router } from '@angular/router';
import { DataValidator } from '../utility/data-validator';
import { CookieService } from 'ngx-cookie-service';
import { ActivatedRoute } from '@angular/router';
import { ServiceLocatorService } from '../service-locator.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  endpoint = "http://localhost:8080/Auth";

  form = {
    error: false,
    message: '',
    loginId: '',
    password: '',
    loginUrl: '',
  };

  inputerror = {};
  message = '';

  userparams = {
    url: '',
    sessionExpiredMsg: '',
    methodType: '',
  };


  constructor(private httpService: HttpServiceService, private dataValidator: DataValidator, private router: Router,
    private cookieService: CookieService, private route: ActivatedRoute, private serviceLocator: ServiceLocatorService) {

  }

  /**
   * Initialize component
   */
  ngOnInit() {

//backend-> se mesage bhej ra session has bean expier.
    this.route.queryParams.subscribe((params) => {
      this.form.message = params['errorMessage'] || null;
      if (this.form.message != null) {
        this.form.error = true;
      }
      console.log('msssssssssgggggggggggg = >', this.form.message)
    });

//front end ki isLogout -httpservice ki ->sessionexpire vali.
    // if(this.httpService.form.error == true){
    //   this.form.message = this.httpService.form.message;
    //   this.form.error = this.httpService.form.error;
    // }
    
    //navbar.component se logout methode se aa ra-Logout karne ke time pr >--->>"/login/true" bhej rahe he.
    var a = '';
    this.serviceLocator.getPathVariable(this.route, function (params) { a = params["userparams"]; })
    if (a == 'true') {
      this.form.message = 'Logout Successfully';
    }
  }




  signIn() {
    var _self = this;
    this.form.error = false;
    const requestedUrl = this.httpService.userparams.url;//to get the URI
    console.log('url===>------->>>>', requestedUrl );
    this.httpService.post(this.endpoint + "/login", this.form, function (res) {

      console.log('MyResponse', res);


      _self.form.message = '';
      _self.inputerror = {};
      _self.form.error = !res.success;


      if (_self.dataValidator.isNotNullObject(res.result.message)) {
        _self.form.message = res.result.message;
      }   

      if (_self.dataValidator.isNotNullObject(res.result.inputerror)) {
        _self.inputerror = res.result.inputerror;
      }

      if (_self.dataValidator.isTrue(res.success)) {

        _self.httpService.setToken(res.result.token);
        localStorage.setItem("loginId", res.result.loginId);
        let tokenStr = "Bearer " + res.result.token;
        localStorage.setItem("token", tokenStr);
        localStorage.setItem("role", res.result.role);
        localStorage.setItem("fname", res.result.fname);
        localStorage.setItem("lname", res.result.lname);
        localStorage.setItem("userid", res.result.data.id);
    



        if (requestedUrl != null && requestedUrl != '') {
          _self.router.navigateByUrl(requestedUrl); 
        } else {
          _self.router.navigateByUrl('dashboard');
        }


        

      }
    });

  }

  signUp() {
    this.router.navigateByUrl('/signup');
  }



  validate() {
    let flag = true;
    flag = flag && this.dataValidator.isNotNull(this.form.loginId);
    console.log(this.form.loginId);
    flag = flag && this.dataValidator.isNotNull(this.form.password);
    console.log(this.form.password);
    return flag;
  }
}