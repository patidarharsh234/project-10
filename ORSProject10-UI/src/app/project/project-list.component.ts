import { Component, OnInit } from '@angular/core';
import { BaseListCtl } from '../base-list.component';
import { ServiceLocatorService } from '../service-locator.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent extends BaseListCtl implements OnInit {

  constructor(public locator:ServiceLocatorService,public route:ActivatedRoute) {

    super(locator.endpoints.PROJECT,locator,route)
   }


}
