package com.cod.market.product.controller;

import com.cod.market.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/adm/product")
public class AdmProductController {
    private final ProductService productService;

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create() {
        return "adm/product/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String createContent(
            @RequestParam("title") String title, @RequestParam("description") String description,
            @RequestParam("price") int price, @RequestParam("thumbnail") MultipartFile thumbnail
    ) {
        productService.create(title, description, price, thumbnail);

        return "redirect:/product/list";
    }

    @PostMapping("/image-upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String uploadEditorImage(@RequestParam("image") final MultipartFile image) {
        return productService.uploadImage(image);
    }
}

