import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Deal } from 'src/app/core/models/deal.type';

@Component({
  selector: 'app-deal-modal',
  templateUrl: './deal-modal.component.html',
  styleUrls: ['./deal-modal.component.scss'],
  standalone: false,
})
export class DealModalComponent  implements OnInit {
  @Input() deal: Deal | null = null;
  @Input() storeLogoUrl: string = '';


  constructor(private readonly modalCtr: ModalController) { }

  ngOnInit() {}

  close() {
    this.modalCtr.dismiss();
  }

  onViewDeal() {
    this.modalCtr.dismiss(this.deal?.dealID, 'viewdeal');
  }
}
