import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Deal } from 'src/app/core/models/deal.type';

@Component({
  selector: 'app-card-component',
  templateUrl: './card-component.component.html',
  styleUrls: ['./card-component.component.scss'],
  standalone: false,
})
export class CardComponent implements OnInit {
  @Input() deal: Deal | null = null;
  @Input() storeLogoUrl?: string = '';
  @Input() isFavorite: boolean = false;
  @Input() layout: 'horizontal' | 'vertical' = 'vertical';
  @Output() favoriteToggled = new EventEmitter<Deal>();
  @Output() dealClicked = new EventEmitter<Deal>();

  constructor() { }

  ngOnInit() { }


  onFavorite(event: Event) {
    event.stopPropagation();
    this.isFavorite = !this.isFavorite;
    this.favoriteToggled.emit(this.deal!);
  }

  onDetailDeal() {
    this.dealClicked.emit(this.deal!);
  }

}
