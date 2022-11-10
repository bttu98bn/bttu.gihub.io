package com.example.third.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.third.entity.cartitem;
import com.example.third.service.CartService;

@RestController
@RequestMapping("/cart")

public class CartController {

  @Autowired
  CartService cartService;

  @GetMapping("/")
  public List<cartitem> ListCart(HttpSession session) {
    HashMap<String, cartitem> Carts = (HashMap<String, cartitem>) session.getAttribute("Carts");
    if (Carts == null) {
      Carts = new HashMap<String, cartitem>();
    }
    List<cartitem> Listcart = new ArrayList<>(Carts.values());
    return Listcart;

  }

  @PostMapping("/add/{code}")
  public void buy(@PathVariable String code, HttpSession session) {
    HashMap<String, cartitem> carts = (HashMap<String, cartitem>) session.getAttribute("Carts");

    if (carts == null) {
      carts = new HashMap<String, cartitem>();
    }
    carts = cartService.Addcart(code, carts);

    session.setAttribute("Carts", carts);
  }

  @PutMapping("/edit/{Code}/{quantity}")
  public void editCart(@PathVariable String Code, @PathVariable("quantity") int quantity, HttpSession session) {
    HashMap<String, cartitem> carts = (HashMap<String, cartitem>) session.getAttribute("Carts");
    if (carts == null) {
      carts = new HashMap<String, cartitem>();
    }
    carts = cartService.updateCart(Code, quantity, carts);
    session.setAttribute("Carts", carts);
    session.setAttribute("totalQuan", cartService.totalQuantity(carts));
    session.setAttribute("totalPrice", cartService.totalPrice(carts));

  }

  @DeleteMapping("/{code}")
  public void remove(@PathVariable String code, HttpSession session) {
    HashMap<String, cartitem> carts = (HashMap<String, cartitem>) session.getAttribute("Carts");
    if (carts == null) {
      carts = new HashMap<String, cartitem>();
    }
    carts = cartService.removecart(code, carts);

  }

}
