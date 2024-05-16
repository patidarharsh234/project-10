import { Component, OnInit } from '@angular/core';
import { BaseCtl } from '../base.component';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent extends BaseCtl {

  constructor(public locator:ServiceLocatorService,public route:ActivatedRoute,public http:HttpClient) {
    super(locator.endpoints.PRODUCT,locator,route);
   }

 
   fileToUpload:File=null;


   
  onFileSelect(files: FileList) {
    this.fileToUpload = files.item(0);
    console.log(this.fileToUpload);

  }
  myFile() {
   let _self=this;
   const formData = new FormData();
   formData.append('file', _self.fileToUpload);
   _self.http.post("http://localhost:8080/product/Image/" + _self.form.data.id, formData).subscribe( data => {
   }, error => {
    _self.form.mesage=  error.result.mesage;
     console.log(error);
   });
  }
  onUpload() {
    this.submit();
    console.log(this.form.data.id + '---- after submit');

  }

   
  //user Add-Update;
  submit() {
    var _self = this;
 
    this.serviceLocator.httpService.post(this.api.save, this.form.data, function (res) {

      _self.form.message = '';
      _self.form.inputerror={};

      _self.form.error=!res.success;

  
      if (res.success) { 
        _self.form.data.id = res.result.data;

        if (_self.fileToUpload) {
          _self.myFile();
        }


        console.log(_self.form.data.id);
        console.log("----------Harsh----------.");

      } 
      if(res.result.message){
        _self.form.message = res.result.message;
      }
      if (res.result.inputerror) {
        _self.form.inputerror = res.result.inputerror;
      }
     

      console.log('FORM', _self.form);
    });
  }


  populateForm(form, data) {
    form.id = data.id;
    console.log(form.id + 'populate form in usercomponent');
     form.productName = data.productName;
     form .productId=data.productId; //unique value
     form.price = data.price; //static Preload
     form.pgender=data.paymentModeID;//Daynamic preload ID
     form.paymentModeName = data.paymentModeName; //preload SE name nikal kr save
     form.dateModified=data.dateModified;
     form.imageId = data.imageId; //image
  
    console.log(form.status + 'status---');
  }
  parseDate(dateString: string): Date {
    if (dateString) {
      return new Date(dateString);
    }
    return null;
  }




 

}
