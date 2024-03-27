import { OnInit, Component, ViewChildren, QueryList, ElementRef } from '@angular/core';
import { ServiceLocatorService } from './service-locator.service';
import { HttpServiceService } from './http-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseCtl } from './base.component';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { element } from '@angular/core/src/render3';

export class BaseListCtl extends BaseCtl {  
  
  @ViewChildren("checkboxes") checkboxes: QueryList<ElementRef>;
  deleteRecordList: any = [];
  isMasterSel:boolean = false ;
  checkList=0;

  constructor(public endpoint, public locator: ServiceLocatorService, public route: ActivatedRoute) {
    super(endpoint, locator, route);    
  }

  /**
   * Initialize component
   */
  ngOnInit() {   
    this.preload();
    this.search();
    this.isMasterSel = false ; //for select all
  }

  //serachparamenter-->search karte us time pr
  submit() {
    this.isMasterSel=false;
    this.form.pageNo=0;
    this.search();
  }

  //me created->reset
  refresh() {
    this.isMasterSel=false;
    this.form.pageNo=0;
    this.form.searchParams={};
    this.search();
  } 


  next() {
    this.form.pageNo++;
     this.isMasterSel = false ; 
     this.search(); 
  //  this.searchOperation('previous');
  }

  previous() {
    if (this.form.pageNo > 0) {
      this.form.pageNo--; 
    this.isMasterSel = false ;
    this.search();
   // this.searchOperation('previous');    
    }
  }

//use krna he y
exit(){  
  location.reload();
}

  
//selectAll
  checkUncheckAll(event) {
    const checked = event.target.checked;
    //singl checkboxes he ->forEach ki help se nikalte ja re and checked krte ja re
    this.checkboxes.forEach((element) => {     
        element.nativeElement.checked = checked;
        console.log(element.nativeElement.firstName)
    });   
  }



  //sare singal checkbox true hone pr ->selectAll checkbox trueS
  checklistUpdate(){

    this.isMasterSel=false;
    this.checkList=0;
    this.checkboxes.forEach((element) =>{
      //ak bar click kiya checbox pr ye methode run-2nd time click kiya checbox pr vaps ye methode run hogi.
if(element.nativeElement.checked){
  this.checkList=this.checkList+1;
}

    });

    if(this.checkList==this.form.list.length){
      this.isMasterSel=true;

    }
    
  }




 //delete 
  deleteMany(){
    this.form.error = false;
    this.deleteRecordList.length=0;
    var isSelectedRecord:boolean = false ;
   
      this.checkboxes.forEach((element) => {
        
        if (element.nativeElement.checked) {
          // console.log('record deleting ' + element.nativeElement.id)
          this.deleteRecordList.push(element.nativeElement.id);
          isSelectedRecord = true ;
        }
      });     

      if(isSelectedRecord){
            console.log('pageNo.........!!!!!!======',this.form.pageNo);
        super.deleteMany(this.deleteRecordList + '?pageNo=' +this.form.pageNo)

      }else{
        this.form.message = "Select Atleast One Record";
          this.form.error = true;
    
    }
    this.isMasterSel = false ;  
  
  }

delete(id) {
  super.delete(id, function () {
    this.search();
  });
}


}
