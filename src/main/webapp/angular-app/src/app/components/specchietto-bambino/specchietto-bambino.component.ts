import {Component, Input, OnInit} from '@angular/core'

@Component({
  selector: 'app-specchietto-bambino',
  templateUrl: './specchietto-bambino.component.html',
  styleUrls: ['./specchietto-bambino.component.css']
})
export class SpecchiettoBambinoComponent implements OnInit {

  @Input() bambino: any

  constructor() {
  }

  ngOnInit(): void {
  }

}
