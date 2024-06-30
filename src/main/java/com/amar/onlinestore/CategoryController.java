package com.amar.onlinestore;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
class CategoryController {

    private final CategoryRepository repository;
    private final ProductsRepository repository2;

    private final CategoryModelAssembler assembler;
    private final ProductsModelAssembler assemblerproduct;

    public CategoryController(CategoryRepository repository, ProductsRepository repository2,
            CategoryModelAssembler assembler, ProductsModelAssembler assemblerproduct) {
        this.repository = repository;
        this.repository2 = repository2;
        this.assembler = assembler;
        this.assemblerproduct = assemblerproduct;
    }

    // Aggregate root
    @GetMapping("/categories")
    CollectionModel<EntityModel<Category>> all() {
        List<EntityModel<Category>> categories = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(categories, linkTo(methodOn(CategoryController.class).all()).withSelfRel());
    }

    @PostMapping("/categories")
    ResponseEntity<?> newCategory(@Valid @ModelAttribute Category newCategory,
            @RequestParam("image") MultipartFile imageFile) {
        List<Products> list = new ArrayList<Products>();
        newCategory.setProducts(list);

        if (!imageFile.isEmpty()) {
            try {
                byte[] imageData = imageFile.getBytes();
                newCategory.setImage(imageData);
            } catch (IOException e) {
                // Handle the exception appropriately
            }
        }

        EntityModel<Category> entityModel = assembler.toModel(repository.save(newCategory));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/categories/{name}/products")
    ResponseEntity<?> newProduct(@Valid @RequestBody Products newProduct, @PathVariable String name,
            @RequestParam("image") MultipartFile imageFile) {
        Category category = repository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));

        try {
            byte[] imageBytes = imageFile.getBytes();
            newProduct.setImage(imageBytes);
        } catch (IOException e) {
            // Handle the exception
        }

        newProduct.setCategory_id(category.getCategory_id());
        repository2.save(newProduct);

        EntityModel<Products> entityModel = assemblerproduct.toModel(newProduct);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Single item
    @GetMapping("/categories/{name}")
    EntityModel<Category> one(@PathVariable String name) {
        Category category = repository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));

        return assembler.toModel(category);
    }

    @PutMapping("/categories/{name}")
    ResponseEntity<?> replaceCategory(@RequestBody Category newCategory, @PathVariable String name) {
        Category updatedCategory = repository.findByName(name)
                .map(category -> {
                    category.setName(newCategory.getName());
                    category.setProducts(newCategory.getProducts());
                    return repository.save(category);
                })
                .orElseGet(() -> {
                    newCategory.setName(name);
                    return repository.save(newCategory);
                });

        EntityModel<Category> entityModel = assembler.toModel(updatedCategory);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Transactional
    @DeleteMapping("/categories/{name}")
    ResponseEntity<?> deleteCategory(@PathVariable String name) {
        repository.deleteByName(name);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{name}/products")
    CollectionModel<EntityModel<Products>> getProductsForPost(@PathVariable String name) {
        Category category = repository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));

        List<EntityModel<Products>> products = category.getProducts().stream()
                .map(assemblerproduct::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(products, linkTo(methodOn(CategoryController.class).all()).withSelfRel());
    }

    @DeleteMapping("/categories/{name}/products/{productid}")
    ResponseEntity<?> deleteProduct(@PathVariable String name, @PathVariable Integer productid) {
        repository2.deleteById(productid);

        return ResponseEntity.noContent().build();
    }
}
