package com.ca.service;

import com.ca.pojo.Cart;

import java.util.List;

public interface DubboCartService {
    List<Cart> findCartListByUserId(Long userId);

    void updateCartNum(Cart cart);

    void deleteCart(Cart cart);

    void addCart(Cart cart);
}
