package com.example.demo.controller;

import com.example.demo.model.IdList;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String listCustomersView(Model model) {
        model.addAttribute("products", productService.readAll());
        model.addAttribute("idList", new IdList());
        return "Products";
    }

    @GetMapping(value = "/editProduct")
    public String editProduct(Model model, @RequestParam Long id) {
        model.addAttribute("product", productService.read(id));
        return "EditProduct";
    }

    @PostMapping(value = "/postProduct")
    public String postProduct(@RequestParam Long id, @RequestParam String name, @RequestParam BigDecimal price) {
        Product newEntity = new Product(name, price);
        productService.update(newEntity, id);
        return "redirect:";
    }

    @PostMapping(value = "/adProduct")
    public String adProduct(@RequestParam String name, @RequestParam BigDecimal price) {
        Product newEntity = new Product(name, price);
        productService.create(newEntity);
        return "redirect:";
    }


    @GetMapping(value = "/addProduct")
    public String addProduct() {
        return "AddProduct";
    }

    @GetMapping (value = "/deleteProducts")
    public String deleteProducts(@ModelAttribute IdList ids){
        for (Long id: ids.getId() ) {
            productService.delete(id);
        }
        return "redirect:";
    }


}
