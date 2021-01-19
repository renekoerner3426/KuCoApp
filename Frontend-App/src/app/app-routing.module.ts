import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DecreeWindowComponent } from './decree-window/decree-window.component';

const routes: Routes = [
  {
    path: '',
    component: DecreeWindowComponent,
    data: null,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
