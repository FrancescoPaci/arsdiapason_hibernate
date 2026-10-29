import {Component, OnInit} from '@angular/core'
import {HttpService} from '../../services/http.service'
import {BsModalService} from 'ngx-bootstrap/modal'
import {AlertModalComponent} from '../alert-modal/alert-modal.component'
import * as moment from 'moment'

declare var $: any;

@Component({
    selector: 'app-insert-child',
    templateUrl: './insert-child.component.html',
    styleUrls: ['./insert-child.component.css']
})
export class InsertChildComponent implements OnInit {

    constructor(private httpService: HttpService, private modalService: BsModalService) { }

    newChild: any = {note: ''}
    newChildOrigin: string = JSON.stringify({note: ''})
    siNo: any = [{ label: 'Si', db: true }, { label: 'No', db: false }]
    sexList: any = [{ label: 'Maschio', db: 'M' }, { label: 'Femmina', db: 'F' }]
    today: string = moment().format('YYYY-MM-DD')
    istituti: any = []
    sezioni: any = []
    textAreaMaxLength: number = 300
    tipoOperazione: string


    ngOnInit(): void {
        this.getListaIstituti()
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
            (error: any) => { },
            () => { }
        )
    }

    insertChild() {
        if(this.tipoOperazione === 'crea') {
          this.newChild.id = null;
        }
        this.httpService.callPost('insertChild', this.newChild).subscribe(
            (data: any) => {
                this.newChild = {note: ''}
                this.openAlert()
            },
            (error: any) => { },
            () => { }
        )
    }

    openAlert() {
        $('#mio-alert').show().css('opacity', '1');
        setTimeout(() => {
            $('#mio-alert').fadeTo(1000, 0, function() {
              $(this).hide();
            });
        }, 1000);
    }

    getInstituteDetails(idIstituto: number) {
        if (idIstituto > 1) {
          const istituto = this.istituti.find((x: { id: number }) => x.id == idIstituto);
          this.sezioni = istituto.sezioni.split(',')
        } else {
          this.newChild.sezione = ''
          this.newChild.classe = ''
        }
    }

    get hasNotChanges(): boolean {
      return JSON.stringify(this.newChild) === this.newChildOrigin;
    }

}
