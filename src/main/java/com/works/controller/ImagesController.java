package com.works.controller;


import com.works.entities.ProductImage;
import com.works.services.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.awt.*;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImagesController {


    Long pid = 0l;
    final ImagesService imagesService;
    List<ProductImage> ls = new ArrayList<>();

    @GetMapping("/images/{pid}")
    public String images(@PathVariable Long pid, Model model){
        this.pid = pid;
        ls = imagesService.list(this.pid);
        model.addAttribute("images", ls);
        return "images";
    }

    @PostMapping("/imageAdd")
    public String imageAdd(@RequestParam("image") MultipartFile file) throws IOException, SQLException {
        ProductImage productImage= new ProductImage();
        productImage.setPid(this.pid);
        byte [] fileBytes = file.getBytes();
        Blob blob = new SerialBlob(fileBytes);
        productImage.setImage(blob);
        imagesService.addImage(productImage);
        return "redirect:/images/"+this.pid;
    }

    @ResponseBody
    @GetMapping (value = "/getImage/{index}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable int index) throws IOException, SQLException {
        Blob blob = ls.get(index).getImage();
        int blobLength = (int) blob.length();
        byte [] image = blob.getBytes(1,blobLength);
        return image;
    }

}
