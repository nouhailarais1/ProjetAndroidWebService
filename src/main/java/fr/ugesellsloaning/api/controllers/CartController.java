package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.entities.Cart;
import fr.ugesellsloaning.api.entities.Product;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.CartServices;
import fr.ugesellsloaning.api.services.ProductServices;
import fr.ugesellsloaning.api.services.UserServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Api( tags={"Operations Cart \"Cart\""})
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartServices cartServices;

    @Autowired
    UserServices userServices;

    @Autowired
    ProductServices productServices;

    @GetMapping(path = "/")
    public Iterable<Cart> list() {
        return cartServices.listCart();
    }

    @GetMapping(path = "add/{product}/{user}")
    public int add(@PathVariable(value = "product") long product, @PathVariable(value = "user")  long user) {
        //current Use
        boolean exist = false;

        User u = userServices.getUserById(user);

        Product product1 = productServices.getProductById(product);
        if (product1 != null) {
            Cart cart = new Cart();
            cart.setUser(u.getId());
            cart.setProduct(product);

            List<Cart> carts = cartServices.getCartByUser(u.getId());
            for (Cart cart1 : carts) {
                if (cart1.getProduct() == cart.getProduct() && cart1.getUser() == cart.getUser()) {
                    exist = true;
                }
            }
            if (exist == false) cartServices.save(cart);
        }
        return cartServices.getProductInCart(u.getId()).size();
    }

    @GetMapping(path = "/id/{id}")
    public Optional<Cart> getById(@PathVariable(value = "id") long id) {
        return cartServices.getCartById(id);
    }


    @GetMapping(path = "/productInCart/{user}")
    public List<Product> getByUser(@PathVariable(value = "user")  long user) {
        User u = userServices.getUserById(user);
        if(u!=null){
            return cartServices.getProductInCart(user);
        }
        return null;
    }

    @GetMapping("/deleteAll/{user}")
    public int deleteCartByUser(@PathVariable(value = "user")  long user){
        User u = userServices.getUserById(user);
        if(u!=null) {
            cartServices.deleteByUser(user);
            return cartServices.getProductInCart(user).size();
        }
        return 0;

    }

    @GetMapping("/product/{product}/{user}")
    public int deleteCartByProduct(@PathVariable(value = "product")  long product,@PathVariable(value = "user")  long user){

        User u = userServices.getUserById(user);
        if(u!=null){
            List<Cart> carts = cartServices.getCartByUser(user);
            for (Cart cart: carts) {
                if(cart.getProduct() == product) {
                    cartServices.deleteByProduct(product);
                    return cartServices.getProductInCart(user).size();
                }
            }
            return 0;
        }
        return -1;
    }

    @GetMapping(path = "/buy/{user}")
    public int buyCart(@PathVariable(value = "user")  long user){
        Double amount = 0.0;
        User u = userServices.getUserById(user);
        if(u != null){
            List<Product> productList = cartServices.getProductInCart(u.getId());
            if (productList != null){
                for (Product product : productList){
                    amount= amount + product.getPrice();
                }
                return cartServices.confirmPurchase(u.getId(), amount);
            }
        }
        return 0;
    }

/*
    @GetMapping(path = "/buy/{user}")
    public boolean buyCart(@PathVariable(value = "user")  long user){
        Double amount = 0.0;
        String email = "mounas2@gmail.com";
        User user1 = userServices.getUserById(user);
        List<Product> productList = cartServices.getProductInCart(user);
        for (Product product : productList){
            amount=amount + product.getPrice();
        }
        return cartServices.confirmPurchase(user, amount);
    }

 */

}
