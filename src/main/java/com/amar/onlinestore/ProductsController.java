package com.amar.onlinestore;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
class ProductsController {

    private final ProductsRepository repository;
    private final CategoryRepository2 repository2;
    private final ProductsModelAssembler assembler;

    ProductsController(ProductsRepository repository, CategoryRepository2 repository2, ProductsModelAssembler assembler) {
        this.repository = repository;
        this.repository2 = repository2;
        this.assembler = assembler;
    }

    // Aggregate root
    @GetMapping("/products")
    CollectionModel<EntityModel<Products>> all() {
        List<EntityModel<Products>> products = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(ProductsController.class).all()).withSelfRel());
    }

    @PostMapping("/products")
    ResponseEntity<?> newProduct(@Valid @ModelAttribute Products newProduct,
    @RequestPart("image") MultipartFile imageFile) {
        Category category = repository2.findById(newProduct.getCategory_id())
                .orElseThrow(() -> new newCategoryNotFoundException());

        // Handle the image file
        try {
            byte[] imageBytes = imageFile.getBytes();
            newProduct.setImage(imageBytes);
        } catch (IOException e) {
            // Handle the exception
        }

        category.getProducts().add(newProduct);
        EntityModel<Products> entityModel = assembler.toModel(repository.save(newProduct));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item
    @GetMapping("/products/{id}")
    EntityModel<Products> one(@PathVariable Integer id) {
        Products product = repository.findById(id)
                .orElseThrow(() -> new ProductsNotFoundException(id));

        return assembler.toModel(product);
    }

    @PutMapping(value = "/products/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
ResponseEntity<?> replaceProduct(
    @Valid @RequestPart Products newProduct,
    @RequestPart("image") MultipartFile image,
    @PathVariable Integer id
) {
    Products updatedProduct = repository.findById(id)
        .map(product -> {
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setPrice(newProduct.getPrice());
            product.setColors(newProduct.getColors());
            product.setBrand(newProduct.getBrand());
            product.setRating(newProduct.getRating());
            product.setProductType(newProduct.getProductType());
            product.setCategory_id(newProduct.getCategory_id());
            product.setTagList(newProduct.getTagList());

            // Handle the image file
            if (!image.isEmpty()) {
                try {
                    byte[] imageData = image.getBytes();
                    product.setImage(imageData);
                } catch (IOException e) {
                    // Handle the exception
                    e.printStackTrace();
                }
            }

            return repository.save(product);
        })
        .orElseGet(() -> {
            newProduct.setProduct_id(id);

            // Handle the image file
            if (!image.isEmpty()) {
                try {
                    byte[] imageData = image.getBytes();
                    newProduct.setImage(imageData);
                } catch (IOException e) {
                    // Handle the exception
                    e.printStackTrace();
                }
            }

            return repository.save(newProduct);
        });

    EntityModel<Products> entityModel = assembler.toModel(updatedProduct);

    return ResponseEntity
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
}

    @DeleteMapping("/products/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/products/{productId}/colors")
    public ResponseEntity<String> addColorsToProduct(
            @PathVariable Integer productId,
            @RequestBody List<String> colors) {

        Optional<Products> productOptional = repository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Products product = productOptional.get();
        product.getColors().addAll(colors);
        repository.save(product);

        return ResponseEntity.ok("Colors added successfully.");
    }


    @PostMapping("products/{productId}/tags")
public ResponseEntity<String> addTagsToProduct(
        @PathVariable Integer productId,
        @RequestBody List<String> tags) {

    Optional<Products> productOptional = repository.findById(productId);
    if (productOptional.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Products product = productOptional.get();
    product.getTagList().addAll(tags);
    repository.save(product);

    return ResponseEntity.ok("Tags added successfully.");
}


@PutMapping("/{id}/image")
    public ResponseEntity<?> updateProductImage(@PathVariable("id") Integer id,@RequestParam byte[] image) {
        try {
            Products product = repository.findById(id)
                    .orElseThrow(() -> new ProductsNotFoundException(id));
            
           // Update the image
           product.setImage(image);
            
           // Save the updated product
           repository.save(product);
           
           return ResponseEntity.ok().build();
       } catch (ProductsNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
   }
}
