package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartService {

    private final ProductRepository productRepository;

    private Map<Product, Integer> products = new HashMap<>();

    @Autowired
    public ShoppingCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }


    public void removeProduct(Product product) {
        if(products.containsKey(product)){
            if (products.get(product)>1) {
                products.replace(product, products.get(product) - 1);
            } else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }


    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    public void checkout() throws NotEnoughProductsInStockException{
        Product product;
        for(Map.Entry<Product, Integer> entry : products.entrySet()){
            product = productRepository.findById(entry.getKey().getProductId()).get();
            entry.getKey().setQuantity(entry.getValue());
        }
        productRepository.saveAll(products.keySet());
        productRepository.flush();
        products.clear();

    }

    public BigDecimal getTotal() {
        return products.entrySet().stream()
                .map(entry -> new BigDecimal(entry.getKey().getPrice()).multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    // calculates shipping cost
    public BigDecimal getShipping(){
        Double shippingCost =0.0;
        if(getTotal().doubleValue()<50.00) {
            //charging 5% shipping cost
            shippingCost = getTotal().doubleValue() * 0.05;
            //rounding up BigDecimal value
            return new BigDecimal(shippingCost).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        else{
            shippingCost = 0.0;
            return new BigDecimal(shippingCost);
        }
    }

    // calculates grand total after shipping cost
    public BigDecimal getGrandTotal(){
        Double grandTotal;
        grandTotal = getTotal().doubleValue() + getShipping().doubleValue();
        //rounding up BigDecimal value
        return new BigDecimal(grandTotal).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
