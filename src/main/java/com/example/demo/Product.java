package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    private String name;
    private String description;
    private String price;
    private String imageURL;
    private boolean active;


    @Column(name = "quantity")
    @Min(value = 0, message = "*Quantity has to be non negative number")
    private Integer quantity;


    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name ="order_id")
   /* @JoinTable(joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="order_id"))*/
    private Order order;


    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private Collection<WishList> wishLists;


    public Product(){}



    public Product(String name, String description, String price, String image, boolean active){
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = image;
        this.active = active;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getProductId() { return productId; }

    public void setProductId(long productId) { this.productId = productId; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Collection<WishList> getWishLists() {
        return wishLists;
    }

    public void setWishLists(Collection<WishList> wishLists) {
        this.wishLists = wishLists;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", active=" + active +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
//        if (!(o instanceof Product))
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId();
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}