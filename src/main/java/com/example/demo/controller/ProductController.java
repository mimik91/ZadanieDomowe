package com.example.demo.controller;

import com.example.demo.model.IdList;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;


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

    @PostMapping(value = "/editProduct")
    public String postProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            bindingResult.addError(new FieldError("product", "product.name", "Nazwa musi mieć znaki"));
            bindingResult.addError(new FieldError("product", "product.price", "Cena może być tylko liczbą i tylko dodatnią"));
            return "EditProduct";
        }
        productService.update(product, product.getId());
        return "redirect:";
    }

    @GetMapping(value = "/addProduct")
    public String addProduct() {
        return "AddProduct";
    }

    @PostMapping(value = "/addProduct")
    public String adProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "AddProduct";
        }
        productService.create(product);
        return "redirect:";
    }

    @GetMapping (value = "/deleteProducts")
    public String deleteProducts(@ModelAttribute IdList ids){
        for (Long id: ids.getId() ) {
            productService.delete(id);
        }
        return "redirect:";
    }
}
