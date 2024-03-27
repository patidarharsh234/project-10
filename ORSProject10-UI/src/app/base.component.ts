import { OnInit } from '@angular/core';
import { ServiceLocatorService } from './service-locator.service';
import { ActivatedRoute } from '@angular/router';
import { HttpServiceService } from './http-service.service';
import { formatNumber } from '@angular/common';
import { listLazyRoutes } from '@angular/compiler/src/aot/lazy_routes';



export class BaseCtl implements OnInit {

  public api = {
    endpoint: null,
    get: null,
    save: null,
    search: null,
    delete: null,
    deleteMany: null,
    preload: null,
    report: null
  }


  initApi(ep) {
    this.api.endpoint = ep;
    this.api.get = ep + "/get";
    this.api.save = ep + "/save";
    this.api.search = ep + "/search";
    this.api.delete = ep + "/delete";
    this.api.deleteMany = ep + "/deleteMany";
    this.api.preload = ep + "/preload";
    this.api.report = ep + "/report";

    console.log("endpoint=>",ep);
    console.log("API",this.api);

  }

  /**
   * Form contains preload data, error/sucess message 
   */
  public form: any = {

    error: false, //error 
    message: null, //error or success message
    inputerror: {}, // form input error messages

    data: { id: null }, //form data
    
    searchParams: {}, //search form
    list: [], // search list 
    preload: [], // preload data
    pageNo: 0,
    
    searchMessage: null//search result message
  };
  nextList = 0;

  /**
   * Initialize services 
   * 
   * @param serviceLocator 
   * @param route 
   */
  constructor(public endpoint, public serviceLocator: ServiceLocatorService, public route: ActivatedRoute) {

    var _self = this;

    _self.initApi(endpoint);

    /**
     * Get primary key from path variale
     */
    serviceLocator.getPathVariable(route, function (params) {
      _self.form.data.id = params["id"];
      console.log('I GOT ID', _self.form.data.id);
    })
  }

  /**
   * Initialize component
   */
  ngOnInit() {
    this.preload();
    if (this.form.data.id && this.form.data.id > 0) {
      this.display();
    }
  }


  forward(page) {
  
    this.serviceLocator.forward(page);
  }


  /**
   * Loded preload data
   */
  preload() {
    console.log("preload start")
    var _self = this;
    this.serviceLocator.httpService.get(_self.api.preload, function success (res) {

      if (res.success) {
        _self.form.preload = res.result;
      } else {
        _self.form.error = true;
        _self.form.message = res.result.message;
      }
      console.log('FORM', _self.form);
    },function fail(error){  console.log(error) }
    
    
    );
  }


  /**
 * Contains display logic. It fetches data from database for the primary key 
 */
  //update pr work
  display() {

    var _self = this;
    console.log(' display method');
    this.serviceLocator.httpService.get(_self.api.get + "/" + _self.form.data.id, function (res) {

      _self.form.data.id = 0;
      _self.form.message = null;
      _self.form.error = !res.success;

      if (res.success) {
        // _self.form.data=res.result.data;//my way directly-fromdatabase.
        _self.populateForm(_self.form.data, res.result.data);
      } else {
        _self.form.data = {};
        //_self.form.data=res.result.data;//may way directy-fromdatabase.
      }
      if (res.result.message) {
        _self.form.message = res.result.message;
      }


      console.log('FORM', _self.form);
    }
    );
  }



  search() {
    var _self = this;
     console.log('search--start');

    this.serviceLocator.httpService.post(_self.api.search + "/" + _self.form.pageNo, _self.form.searchParams, function (res) {

      _self.form.error = !res.success;
      _self.form.message = null;

      if (res.result.message) {
        _self.form.message = res.result.message;
      }
      //res.false aayega hi nhi vha backend se false kr ke nahi bheja.
      if (res.success) {
        _self.form.list = res.result.data;
        _self.nextList = res.result.nextList;
      } else {
        //run time pr analysis->res.success false hoga to ->error bhi false hona chahiye.
        //  _self.form.error = ! res.success;
        _self.form.list = [];//list clear karne ke liye.
        _self.nextList = 0;//next disabel ke liye.
      }

    });
  }





  
  /**
   * Contains submit logic. It saves data
   */


  submit() {
    var _self = this;

    console.log( "submit start running",this.form);
    console.log("form data going to be submit" + this.form.data);
    this.serviceLocator.httpService.post(this.api.save, this.form.data, function (res) {
      _self.form.message = '';
      _self.form.inputerror = {};
      _self.form.error = false;

      _self.form.error = !res.success;

      if (res.result.inputerror) {
        _self.form.inputerror = res.result.inputerror;
      }

      if (res.result.message) {
        _self.form.message = res.result.message;
      }

      if (res.success) {
        _self.form.data.id = res.result.data;
        //return _self.form.data.id ;
      }
      //agr new data add ke bad add khule hi khulvana to ho Id=0 kar do.
      _self.form.data.id = res.result.data;
      console.log('FORM', _self.form);
    });
  }


  deleteMany(id) {
    var _self = this;                 //(http://localhost:8080/User/deleteManey+ "/" + deleteRecourdList + "?pageNo=" +this.form.pageNo ,this.serachParam,function(res) {  } )
                                      //(http://localhost:8080/User/deleteManey /id?pageNo=0, searchParam)
    this.serviceLocator.httpService.post(_self.api.deleteMany + "/" + id, this.form.searchParams, function (res) {
      _self.form.error = !res.success;
      _self.form.message = '';

      if (res.result.message) {
        _self.form.message = res.result.message;
      }

      if (res.success) {
        _self.form.list = res.result.data;
        _self.nextList = res.result.nextList;

      } else {
        _self.form.list = [];
        _self.nextList = 0;
      }
    });
  }

  generateReport() {
    var _self = this;
    console.log('********* Generating Report ********************');
    this.serviceLocator.httpService.get(_self.api.report, function (res) {

      if (res.success) {
        console.log('*********  Report Generated ********************');
        alert('pass');

      } else {
        console.log('********* Error in Generating Report  ********************');
        alert('fail');
      }
    });

  }


  /**
   * Searhs records 
   */


  searchOperation(operation: String) {
    var _self = this;
    console.log("Search Form", _self.form.searchParams);
    this.serviceLocator.httpService.post(_self.api.search + "/" + _self.form.pageNo, _self.form.searchParams, function (res) {


      if (operation == 'next' || operation == 'previous') {
        _self.nextList = res.result.nextList;
        _self.form.message = null;
        _self.form.error = false;
      }

      if (res.success) {
        _self.form.list = res.result.data;
        if (_self.form.list.length == 0) {
          _self.form.message = "No record found";
          _self.form.error = true;
        }
        console.log("List Size", _self.form.list.length);
      } else {
        _self.form.error = false;
        _self.form.message = res.result.message;
      }
      console.log('FORM', _self.form);
    });
  }

  delete(id, callback?) {
    var _self = this;
    this.serviceLocator.httpService.get(_self.api.delete + "/" + id, function (res) {
      if (res.success) {
        _self.form.message = "Data is deleted";
        if (callback) {
          console.log('Response Success and now Calling Callback');
          //  callback();  
          this.search();
        }
      } else {
        _self.form.error = true;
        _self.form.message = res.result.message;
      }
    });
  }

  /**
   * Forward to page
   * @param page 
   */



  validate() {
    return this.validateForm(this.form.data);
  }

  /**
   * Override by childs 
   * 
   * @param form 
   */
  validateForm(form) {
  }


    /**
 * Populate HTML form data
 * Overridden by child classes.
 * 
 * @param form 
 * @param data 
 */
    populateForm(form, data) {

      form.id = data.id;
      console.log(form.id + 'formid in base ctl');
    }

}