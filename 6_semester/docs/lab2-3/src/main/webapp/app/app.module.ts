import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BookingSharedModule } from 'app/shared/shared.module';
import { BookingCoreModule } from 'app/core/core.module';
import { BookingAppRoutingModule } from './app-routing.module';
import { BookingHomeModule } from './home/home.module';
import { BookingEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    BookingSharedModule,
    BookingCoreModule,
    BookingHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BookingEntityModule,
    BookingAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class BookingAppModule {}
