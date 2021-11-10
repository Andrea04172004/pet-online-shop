import {Injector, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CookieService} from 'ngx-cookie-service';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AddCategoryComponent} from './pages/add-category/add-category.component';
import {AddProductComponent} from './pages/add-product/add-product.component';

import {FindProductComponent} from './pages/find-product/find-product.component';
import {HeaderMenuComponent} from './pages/header-menu/header-menu.component';
import {LoginComponent} from './pages/login/login.component';
import {ProductDashboardComponent} from './pages/product-dashboard/product-dashboard.component';

import {UpdateProductComponent} from './pages/update-product/update-product.component';

import {JwtInterceptor} from './utils/JwtInterceptor';

import {SearchfilterPipe} from './searchfilter.pipe';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {CounterDirective} from "./services/counter-directive";
import {CartPageComponent} from "./pages/cart-page/cart-page.component";


import {ProductDetailsComponent} from './pages/product-details/product-details.component';
import {OrderDashboardComponent} from './pages/order-dashboard/order-dashboard.component';
import {RegisterComponent} from "./pages/register/register.component";

import {OrderDetailsComponent} from "./pages/order-details/order-details.component";
import {UserDetailsComponent} from './pages/user-details/user-details.component';
import {BillingDetailsComponent} from './pages/billing-details/billing-details.component';
import {NgxMaskModule} from "ngx-mask";
import {TextMaskModule} from 'angular2-text-mask';
import {PaymentPage} from "./pages/payment-page/payment-page";
import {MatRadioModule} from "@angular/material/radio";
import {OrderConfirmationComponent} from './pages/order-confirmation/order-confirmation.component';
import {SideNavbarComponent} from './pages/side-navbar/side-navbar.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatTabsModule} from "@angular/material/tabs";
import {LuhnTestComponent} from './pages/luhn-test/luhn-test.component';
import {FormFieldCustomControlExampleComponent} from './pages/add-phone-form/form-field-custom-control-example.component';
import {DemoMaterialModule} from "./material-module";



import {SidebarModule} from "ng-sidebar";
import {FullCheckoutComponent} from './pages/full-checkout/full-checkout.component';
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {AnimatedButtonComponent} from './pages/anumated-button/animated-button.component';
import {WishListComponent} from './pages/wish-list/wish-list.component';
import {MoveProductDialog} from "./pages/wish-list-dialog/move-product-dialog.component";
import {DeliveryDetailsComponent} from './pages/delivery-details/delivery-details.component';
import {AddAddressFormComponent} from './pages/add-address-form/add-address-form.component';

import {UserListComponent} from './pages/user-list/user-list.component';
import {EditUserComponent} from './pages/edit-user/edit-user.component';
import {MatConfirmDialogComponent} from './pages/mat-confirm-dialog/mat-confirm-dialog.component';


import { AddToCartButtonComponent } from './pages/add-to-cart-button/add-to-cart-button.component';
import { AddNewWishListDialogComponent } from './pages/add-new-wish-list-dialog/add-new-wish-list-dialog.component';
import {AllProductsComponent} from "./pages/all-products/all-products.component";
import { AddProductToWishListDialogComponent } from './pages/add-product-to-wish-list-dialog/add-product-to-wish-list-dialog.component';
import { EditTitleWishlistComponent } from './pages/edit-title-wishlist/edit-title-wishlist.component';
import { MyTelInput } from './pages/my-tel-input/my-tel-input.component';
import {EventEmitterService} from "./services/event-emitter.service";



export let InjectorInstance: Injector;

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    AddProductComponent,
    AddCategoryComponent,
    UpdateProductComponent,
    LoginComponent,
    LoginComponent,

    HeaderMenuComponent,

    ProductDashboardComponent,

    FindProductComponent,

    SearchfilterPipe,
    AllProductsComponent,
    CounterDirective,
    CartPageComponent,


    ProductDetailsComponent,
    OrderDashboardComponent,

    OrderDetailsComponent,
    UserDetailsComponent,
    BillingDetailsComponent,
    PaymentPage,
    OrderConfirmationComponent,
    SideNavbarComponent,


    PaymentPage,
    LuhnTestComponent,
    FormFieldCustomControlExampleComponent,
    MyTelInput,



    FullCheckoutComponent,
    AnimatedButtonComponent,
    WishListComponent,
    MoveProductDialog,
    DeliveryDetailsComponent,
    AddAddressFormComponent,





    UserListComponent,


    EditUserComponent,


    MatConfirmDialogComponent,


    AddToCartButtonComponent,


    AddNewWishListDialogComponent,


    AddProductToWishListDialogComponent,


    EditTitleWishlistComponent,


    MyTelInput,







  ],

  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatRadioModule,
    NgxMaskModule.forRoot(),
    TextMaskModule,
    MatTabsModule,
    MatSidenavModule,
    DemoMaterialModule,
    SidebarModule,
    FontAwesomeModule,

  ],

  providers: [CookieService, AnimatedButtonComponent, EventEmitterService,
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'fill'}},
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
  ], // fix me, add cookies provider
  // tslint:disable-next-line:object-literal-sort-keys
  bootstrap: [AppComponent],
})
export class AppModule {
  constructor(private injector: Injector) {
    InjectorInstance = this.injector;
  }
}
