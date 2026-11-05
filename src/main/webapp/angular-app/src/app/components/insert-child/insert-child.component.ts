import {Component, OnInit} from '@angular/core'
import {HttpService} from '../../services/http.service'
import * as moment from 'moment'

declare var $: any;

@Component({
  selector: 'app-insert-child',
  templateUrl: './insert-child.component.html',
  styleUrls: ['./insert-child.component.css']
})
export class InsertChildComponent implements OnInit {

  newChild: any = {}
  newChildOrigin: string = JSON.stringify({})
  siNo: any = [{label: 'Si', db: true}, {label: 'No', db: false}]
  sexList: any = [{label: 'Maschio', db: 'M'}, {label: 'Femmina', db: 'F'}]
  today: string = moment().format('YYYY-MM-DD')
  istituti: any = []
  sezioni: any = []
  textAreaMaxLength: number = 300
  tipoOperazione: string
  classi: any = []
  anniScolastici: any = [];

  constructor(private httpService: HttpService) {
  }

  get hasNotChanges(): boolean {
    return JSON.stringify(this.newChild) === this.newChildOrigin;
  }

  ngOnInit(): void {
    this.getListaIstituti()
    this.getClassi()
    this.generaAnniScolastici(20);
  }

  getClassi() {
    this.httpService.callGet('getClassi').subscribe(
      (data: any) => {
        this.classi = data
      },
      (error: any) => {
      },
      () => {
      }
    )
  }

  getListaIstituti() {
    this.httpService.callPost('getListaIstituti', null).subscribe(
      (data: any) => {
        this.istituti = data
        if (history.state && history.state.data) {
          this.newChild = history.state.data
          this.newChildOrigin = JSON.stringify(history.state.data)
          this.getInstituteDetails(history.state.data.idIstituto)
        }
      },
      (error: any) => {
      },
      () => {
      }
    )
  }

  insertChild() {
    if (this.tipoOperazione === 'crea') {
      this.newChild.id = null;
    }
    this.httpService.callPost('insertChild', this.newChild).subscribe(
      (data: any) => {
        this.newChild = {}
        this.openAlert()
      },
      (error: any) => {
      },
      () => {
      }
    )
  }

  openAlert() {
    $('#mio-alert').show().css('opacity', '1');
    setTimeout(() => {
      $('#mio-alert').fadeTo(1000, 0, function () {
        $(this).hide();
      });
    }, 1000);
  }

  getInstituteDetails(idIstituto: number) {
    if (idIstituto > 1) {
      const istituto = this.istituti.find((x: { id: number }) => x.id == idIstituto);
      this.sezioni = istituto?.sezioni?.split(',')
    } else {
      this.newChild.sezione = null
      this.newChild.classe = null
    }
  }

  generaAnniScolastici(anniIndietro: number) {
    const annoCorrente: number = new Date().getFullYear();
    for (let i: number = 0; i <= anniIndietro; i++) {
      const annoInizio: number = annoCorrente - i;
      const annoFine: number = annoInizio + 1;
      this.anniScolastici.push(`${annoInizio}/${annoFine}`);
    }
  }

}
