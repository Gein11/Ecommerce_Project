import { UserResponse } from 'src/app/responses/user/user.response';
import { Injectable } from '@angular/core';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})

export class CartService {
  private cart: Map<number, number> = new Map(); // Dùng Map để lưu trữ giỏ hàng, key là id sản phẩm, value là số lượng

  constructor() {
    // Lấy dữ liệu giỏ hàng từ localStorage khi khởi tạo service    
    // const storedCart = localStorage.getItem(this.getCartKey());
    // if (storedCart) {
    //   this.cart = new Map(JSON.parse(storedCart));      
    // }
    this.refreshCart();
  }
  private refreshCart():void {
    const storeCart = localStorage.getItem(this.getCartKey());
    if (storeCart) {
      this.cart = new Map(JSON.parse(storeCart));
    } else {
      this.cart = new Map<number,number>();
    }
  }
  private getCartKey():string{
    const userResponseJSON = localStorage.getItem('user');
    const userResponse = JSON.parse(userResponseJSON!);
    return `cart:${userResponse.id}`
  }

  addToCart(productId: number, quantity: number = 1): void {
    debugger
    if (this.cart.has(productId)) {
      // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng lên `quantity`
      this.cart.set(productId, this.cart.get(productId)! + quantity);
    } else {
      // Nếu sản phẩm chưa có trong giỏ hàng, thêm sản phẩm vào với số lượng là `quantity`
      this.cart.set(productId, quantity);
    }
     // Sau khi thay đổi giỏ hàng, lưu trữ nó vào localStorage
    this.saveCartToLocalStorage();
  }
  
  getCart(): Map<number, number> {
    return this.cart;
  }
  setCart(cart: Map<number, number>): void {
    this.cart = cart ?? new Map<number, number>();
    this.saveCartToLocalStorage();
  }
  // Lưu trữ giỏ hàng vào localStorage
  private saveCartToLocalStorage(): void {
    debugger
    localStorage.setItem(this.getCartKey(), JSON.stringify(Array.from(this.cart.entries())));
  }  
  // Hàm xóa dữ liệu giỏ hàng và cập nhật Local Storage
  clearCart(): void {
    this.cart.clear(); // Xóa toàn bộ dữ liệu trong giỏ hàng
    this.saveCartToLocalStorage(); // Lưu giỏ hàng mới vào Local Storage (trống)
  }
}
