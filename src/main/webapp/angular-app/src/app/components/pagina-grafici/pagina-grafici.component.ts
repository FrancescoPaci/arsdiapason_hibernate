import {Component, OnInit} from '@angular/core'
import {HttpService} from '../../services/http.service'
import * as _ from 'lodash'
import {fromEvent} from 'rxjs'
import {debounceTime} from 'rxjs/operators'
declare var $: any;

@Component({
   selector: 'app-pagina-grafici',
   templateUrl: './pagina-grafici.component.html',
   styleUrls: ['./pagina-grafici.component.css']
})
export class PaginaGraficiComponent implements OnInit {

   constructor(private httpService: HttpService) { }

   ngOnInit(): void {
      this.getAllTests()
   }

   ngOnDestroy() {
      this.resize.unsubscribe()
   }

   istitutiOrigin: any = []
   sezioniOrigin: any = []
   classiOrigin: any = []
   provaSchedaList: any = []
   searchCombos: any = ['regione', 'provincia', 'comune', 'istituto', 'classe', 'sezione']
   searchLists: any = { regione: [], provincia: [], comune: [], istituto: [], classi: [], sezione: [] }
   searchModels: any = { regione: {}, provincia: {}, comune: {}, istituto: {}, classe: {}, sezione: {} }
   anniArray: any = [5, 6, 7, 8]
   type: string
   data: any[] = []
   columns: any[] = []
   options: any = {}
   width: number
   height: number
   showChart = false
   listaBambini: any = []
   listaBambiniOrigin: any = []

   getAllTests() {
            this.httpService.callPost('getListaIstituti', null).subscribe(
               (data: any) => {
                 this.httpService.callPost('getAllTests', null).subscribe(
                   (testData: any) => {
                     let ids = []
                     for (let i = 0; i < testData.length; i++) {
                       this.listaBambiniOrigin.push(testData[i].bambino)
                       let proveSchede = testData[i].proveSchede
                       for (let j = 0; j < proveSchede.length; j++) {
                         let idIstituto = testData[i].bambino.idIstituto
                         if (!ids.includes(idIstituto)) {
                           ids.push(idIstituto)
                         }
                         proveSchede[j] = { ...proveSchede[j], ..._.find(data, u => u.id == idIstituto) }
                         proveSchede[j].sezione = testData[i].bambino.sezione
                         proveSchede[j].classe = testData[i].bambino.classe
                       }
                       this.provaSchedaList = this.provaSchedaList.concat(proveSchede)
                       if (!this.sezioniOrigin.includes(testData[i].bambino.sezione)) {
                         this.sezioniOrigin.push(testData[i].bambino.sezione)
                       }
                       if (!this.classiOrigin.includes(testData[i].bambino.classe)) {
                         this.classiOrigin.push(testData[i].bambino.classe)
                       }
                     }
                     this.istitutiOrigin =  _.filter(data, i => _.includes(ids, i.id));
                     this.initCombos()
                     this.createChartData()
               },
               (error: any) => { },
               () => { }
            )
         },
         (error: any) => { },
         () => { }
      )
   }

   initCombo(combo: string) {
      return _(this.listaBambini).filter(o => o[combo]).groupBy(combo).keys().value();
   }

   initCombos() {
      this.searchLists.istituto = this.istitutiOrigin
      this.searchLists.regione = _(this.istitutiOrigin).groupBy('regione').keys().value()
      this.searchLists.provincia = _(this.istitutiOrigin).groupBy('provincia').keys().value()
      this.searchLists.comune = _(this.istitutiOrigin).groupBy('comune').keys().value()
      this.searchLists.classe = this.initCombo('classe')
      this.searchLists.sezione = this.initCombo('sezione')
   }

   resetCombos() {
      this.searchModels = { regione: {}, provincia: {}, comune: {}, istituto: {}, classi: {}, sezione: {} }
      this.initCombos()
      this.createChartData()
   }

    comboDisabled(combo: string) {
      return (combo === 'sezione' || combo === 'classe') &&
      !(this.searchModels.istituto && this.searchModels.istituto.id > 1 && this.searchLists[combo].length)
    }

    findByCombo(combo: string) {
      if (combo === 'istituto') {
        this.searchLists.istituto = [this.searchModels[combo]]
        this.listaBambini = _.filter(this.listaBambiniOrigin, ['idIstituto', this.searchModels[combo].id ])
      } else if (combo == 'classe' || combo == 'sezione') {
        this.listaBambini = _.filter(this.listaBambini, [combo, this.searchModels[combo] ])
      } else {
        this.searchLists.istituto = _.filter(this.istitutiOrigin, [combo, this.searchModels[combo]]);
        let ids = _.map(this.searchLists.istituto, 'id');
        this.listaBambini = _.filter(this.listaBambiniOrigin, bambino => _.includes(ids, bambino.idIstituto));
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

      this.createChartData()
    }

   createChartData() {
      const filtri = _.pickBy({
        regione: this.searchModels?.regione,
        provincia: this.searchModels?.provincia,
        comune: this.searchModels?.comune,
        idIstituto: this.searchModels?.istituto?.id,
        classe: this.searchModels?.classe,
        sezione: this.searchModels?.sezione
      }, v => v !== null && v !== undefined && v !== '' && !_.isEmpty(v));
      let proveScheda = _.filter(this.provaSchedaList, filtri);
      let data: any[] = []
      if (proveScheda.length) {
         for (let i = 0; i < 12; i++) {
            let scheda: any
            let label: string
            if (i === 0) {
               label = 'Scheda' + (i + 1) + ' (Copia)'
               scheda = _.filter(proveScheda, { 'numeroScheda': i + 1, 'tipo': 'Copia' })
            } else if (i === 1) {
               label = 'Scheda' + i + ' (Memoria)'
               scheda = _.filter(proveScheda, { 'numeroScheda': i, 'tipo': 'Memoria' })
            } else {
               label = 'Scheda' + i
               scheda = _.filter(proveScheda, { 'numeroScheda': i })
            }
            data.push([label, 0, 0, 0, 0])
            if (scheda.length) {
               for (let j = 0; j < this.anniArray.length; j++) {
                  data[i][j + 1] = _.filter(scheda, { 'anni': this.anniArray[j] }).length
               }
            }
         }
      }
      this.createChart(data)
   }

   createChart(data: any) {
      if (data.length) {
         this.data = data
         this.type = 'ComboChart'
         this.columns = ['Scheda', 'anni 5', 'anni 6', 'anni 7', 'anni 8']
         this.options = {
            vAxis: {
               textStyle: { color: 'black', fontSize: '15', bold: true, italic: false },
            },
            hAxis: { textStyle: { color: 'black', fontSize: '15', bold: true, italic: false } },
            seriesType: 'bars',
            isStacked: 'percent', //true
            //series: { 4: { type: 'line', pointShape: 'circle' } },
            //pointSize: 8,
            legend: { position: 'top' }
         }
         this.showChart = true
         this.resizeChart()
      } else {
         this.data = []
      }
   }

   resize = fromEvent(window, 'resize').pipe(debounceTime(500)).subscribe(() => {
      this.resizeChart()
   })

   resizeChart() {
      let div = $("#chart-div")
      let w = div.width()
      let h = div.width() / 3
      this.options['chartArea'] = { left: w / 100 * 5, right: w / 100 * 5, top: h / 100 * 10, bottom: h / 100 * 8, width: '100%', height: '100%' }
      this.width = w
      this.height = h
   }

}
