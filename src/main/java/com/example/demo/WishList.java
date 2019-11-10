package com.example.demo;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long wishlistId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany
    private Collection<Product> products;

    public long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    public String productQuantity(){
        return Integer.toString(getProducts().size());
    }
}
