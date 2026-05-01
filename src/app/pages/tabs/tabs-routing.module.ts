import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: '',
    component: TabsPage,
    children: [
      {path: 'home', loadChildren: () => import('../tabs/home/home.module').then(m => m.HomePageModule)},
      {path: 'favorite', loadChildren: () => import('../tabs/favorites/favorites.module').then(m => m.FavoritesPageModule)},
      {path: '', redirectTo: 'home', pathMatch: 'full'}
    ]
  }
];

@NgModule({ 
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TabsPageRoutingModule {}
