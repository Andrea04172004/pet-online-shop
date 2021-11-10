import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddCategoryComponent} from './pages/add-category/add-category.component';
import {AddProductComponent} from './pages/add-product/add-product.component';

import {LoginComponent} from './pages/login/login.component';
import {ProductDashboardComponent} from './pages/product-dashboard/product-dashboard.component';

import {UpdateProductComponent} from './pages/update-product/update-product.component';

import {AllProductsComponent} from "./pages/all-products/all-products.component";
import {CartPageComponent} from "./pages/cart-page/cart-page.component";


import {ProductDetailsComponent} from "./pages/product-details/product-details.component";
import {OrderDashboardComponent} from "./pages/order-dashboard/order-dashboard.component";
import {RegisterComponent} from "./pages/register/register.component";

import {OrderDetailsComponent} from "./pages/order-details/order-details.component";
import {UserDetailsComponent} from "./pages/user-details/user-details.component";
import {BillingDetailsComponent} from "./pages/billing-details/billing-details.component";
import {PaymentPage} from "./pages/payment-page/payment-page";
import {OrderConfirmationComponent} from "./pages/order-confirmation/order-confirmation.component";
import {AuthGuard} from "./guards/auth.guard";
import {UserRoles} from "./model/enums/user-roles.enum";
import {LuhnTestComponent} from "./pages/luhn-test/luhn-test.component";



import {FullCheckoutComponent} from "./pages/full-checkout/full-checkout.component";
import {DeliveryDetailsComponent} from "./pages/delivery-details/delivery-details.component";


import {UserListComponent} from "./pages/user-list/user-list.component";

import {AnimatedButtonComponent} from "./pages/anumated-button/animated-button.component";
import {WishListComponent} from "./pages/wish-list/wish-list.component";
import {AddToCartButtonComponent} from "./pages/add-to-cart-button/add-to-cart-button.component";


const routes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'add-product', component: AddProductComponent},
  {path: 'add-category', component: AddCategoryComponent},
  {path: 'product/update/:id', component: UpdateProductComponent},
  {path: 'login', component: LoginComponent},

  {path: 'product-dashboard', redirectTo: 'store/product-dashboard', pathMatch: 'full'},
  {
    path: 'store/product-dashboard', component: ProductDashboardComponent,
    canActivate: [AuthGuard],
    data: {
      role: [UserRoles.ADMIN, UserRoles.MANAGER]
    }
  },


  {path: 'all-products', component: AllProductsComponent},
  {path: 'cart', component: CartPageComponent},


  {path: 'product-details/:id', component: ProductDetailsComponent},
  {
    path: 'order-dashboard',
    component: OrderDashboardComponent,
    canActivate: [AuthGuard],
    data: {
      role: [UserRoles.ADMIN, UserRoles.MANAGER]
    }
  },

  {path: 'order-details/:id',
    component: OrderDetailsComponent,
    canActivate: [AuthGuard],
    data: {role: [UserRoles.ADMIN, UserRoles.MANAGER]}
  },
  {path: 'user-details/:id', component: UserDetailsComponent},
  {path: 'user-details', component: UserDetailsComponent},
  {path: 'billing-details', component: BillingDetailsComponent},
  {path: 'payment', component: PaymentPage},
  {path: 'luhn', component: LuhnTestComponent},

  {path: 'order-confirmation', component: OrderConfirmationComponent},



  {path: 'full-checkout', component: FullCheckoutComponent},
  {path: 'anim-btn', component: AnimatedButtonComponent},
  {path: 'wish-list', component: WishListComponent},
  {path: 'full-checkout', component: FullCheckoutComponent},
  {path: 'delivery-details', component: DeliveryDetailsComponent},


  {path: 'user-list', component: UserListComponent},

  {path: 'add-to-cart-button', component: AddToCartButtonComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
