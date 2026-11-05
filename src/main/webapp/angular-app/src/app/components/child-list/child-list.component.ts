import {Component, OnInit} from '@angular/core'
import {Router} from '@angular/router'
import {HttpService} from '../../services/http.service'
import * as _ from 'lodash'

@Component({
  selector: 'app-child-list',
  templateUrl: './child-list.component.html',
  styleUrls: ['./child-list.component.css']
})
export class ChildListComponent implements OnInit {

  istitutiOrigin: any
  listaBambiniOrigin: any = []
  listaBambini: any = []
  listaBambiniSearch: any = null
  searchCombos: any = ['regione', 'provincia', 'comune', 'istituto', 'classe', 'sezione']
  searchLists: any = {regione: [], provincia: [], comune: [], istituto: [], classe: [], sezione: []}
  searchModels: any = {regione: {}, provincia: {}, comune: {}, istituto: {}, classe: {}, sezione: {}}
  searchCognome: string

  constructor(private httpService: HttpService, private router: Router) {
  }

  ngOnInit(): void {
    this.childsList()
    this.getListaIstituti()
  }

  childsList() {
    this.httpService.callPost('childsList', null).subscribe(
      (data: any) => {
        this.listaBambini = data
        this.listaBambiniOrigin = data
        this.searchLists.sezione = this.initCombo('sezione')
        this.searchLists.classe = this.initCombo('classe')
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
        this.istitutiOrigin = data
        this.initCombos()
      },
      (error: any) => {
      },
      () => {
      }
    )
  }

  getNomeIstituto(idIstituto: number) {
    let inst = _.find(this.istitutiOrigin, ['id', idIstituto])
    if (inst) return inst.nome
  }

  searchChild() {
    let that = this
    if (that.searchCognome) {
      this.listaBambiniSearch = _.filter(this.listaBambini, function (o) {
        return o.cognome && o.cognome.toLowerCase().startsWith(that.searchCognome.toLowerCase())
      })
    } else {
      this.listaBambiniSearch = null
    }
  }

  deleteChild(idBambino: number) {
    this.httpService.callPost('deleteChild?idBambino=' + idBambino, null).subscribe(
      (data: any) => {
        this.childsList()
      },
      (error: any) => {
      },
      () => {
      }
    )
  }

  goToTest(childId: number) {
    this.router.navigate(['testBalconi', childId])
  }

  goToTestResult(childId: number) {
    this.router.navigate(['testResult', childId])
  }

  initCombos() {
    this.searchLists.istituto = this.istitutiOrigin
    this.searchLists.regione = _(this.istitutiOrigin).groupBy('regione').keys().value()
    this.searchLists.provincia = _(this.istitutiOrigin).groupBy('provincia').keys().value()
    this.searchLists.comune = _(this.istitutiOrigin).groupBy('comune').keys().value()
  }

  initCombo(combo: string) {
    return _(this.listaBambini).filter(o => o[combo]).groupBy(combo).keys().value();
  }

  resetCombos() {
    this.searchModels = {regione: {}, provincia: {}, comune: {}, istituto: {}, classe: {}, sezione: {}}
    this.listaBambini = this.listaBambiniOrigin
    this.initCombos()
    this.searchLists.classe = this.initCombo('classe')
    this.searchLists.sezione = this.initCombo('sezione')
  }

  findByCombo(combo: string) {
    if (combo === 'istituto') {
      this.searchLists.istituto = [this.searchModels[combo]]
      this.listaBambini = _.filter(this.listaBambini, ['idIstituto', this.searchModels[combo].id])
    } else if (combo == 'classe' || combo == 'sezione') {
      this.listaBambini = _.filter(this.listaBambini, [combo, this.searchModels[combo]])
    } else {
      this.searchLists.istituto = _.filter(this.istitutiOrigin, [combo, this.searchModels[combo]]);
      let ids = _.map(this.searchLists.istituto, 'id');
      this.listaBambini = _.filter(this.listaBambini, bambino => _.includes(ids, bambino.idIstituto));
    }
    this.searchLists.regione = _(this.searchLists.istituto).groupBy('regione').keys().value()
    this.searchLists.provincia = _(this.searchLists.istituto).groupBy('provincia').keys().value()
    this.searchLists.comune = _(this.searchLists.istituto).groupBy('comune').keys().value()
    this.searchLists.classe = this.initCombo('classe')
    this.searchLists.sezione = this.initCombo('sezione')

    this.searchModels.regione = this.searchLists.regione.length === 1 ? this.searchLists.regione[0] : {}
    this.searchModels.provincia = this.searchLists.provincia.length === 1 ? this.searchLists.provincia[0] : {}
    this.searchModels.comune = this.searchLists.comune.length === 1 ? this.searchLists.comune[0] : {}
    this.searchModels.istituto = this.searchLists.istituto.length === 1 ? this.searchLists.istituto[0] : {}
    this.searchModels.classe = this.searchLists.classe.length === 1 ? this.searchLists.classe[0] : {}
    this.searchModels.sezione = this.searchLists.sezione.length === 1 ? this.searchLists.sezione[0] : {}
  }

  comboDisabled(combo: string) {
    return (combo === 'sezione' || combo === 'classe') &&
      !(this.searchModels.istituto && this.searchModels.istituto.id > 1 && this.searchLists[combo].length)
  }

  getColSpan(combo: string) {
    return combo === 'sezione' || combo === 'classe' ? 'col-1' : 'col-2'
  }

  modifyChild(bambino: any) {
    this.router.navigate(['/insertChild'], {
      state: {data: bambino}
    });
  }

}
