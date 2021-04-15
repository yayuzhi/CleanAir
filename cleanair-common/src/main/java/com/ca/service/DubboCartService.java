package com.ca.service;

import com.ca.pojo.Cart;

import java.util.List;

/**
 * @author 肖骁
 */
public interface DubboCartService {
    /**
     * fetch data by rule id
     * @param userId json format context
     * @return List<cart>
     */
    List<Cart> findCartListByUserId(Long userId);

    /**
     * do rule id
     * @param cart
     */
    void updateCartNum(Cart cart);

    /**
     * 
     * @param cart
     */
    void deleteCart(Cart cart);

    void addCart(Cart cart);
}
