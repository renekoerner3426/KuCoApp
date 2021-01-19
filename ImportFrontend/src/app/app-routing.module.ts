import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { OverviewComponent } from './overview/overview.component';


const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    data: null,
  },
  {
    path: 'overview',
    component: OverviewComponent,
    data: null,
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
