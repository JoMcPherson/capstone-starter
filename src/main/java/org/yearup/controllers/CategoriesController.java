package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.List;


@RestController
// add the annotation to make this controller the endpoint for the following url
// http://localhost:8080/categories
@RequestMapping("/categories")
// add annotation to allow cross site origin requests
@CrossOrigin
public class CategoriesController
{
    private CategoryDao categoryDao;
    private ProductDao productDao;


    // create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // add the appropriate annotation for a get action
    @GetMapping
    public ResponseEntity<List<Category>>getAll()
    {
        // find and return all categories
        var categories = categoryDao.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // add the appropriate annotation for a get action
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable int id) {
        Category category = categoryDao.getById(id);

        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public ResponseEntity<List<Product>> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        var products = productDao.listByCategoryId(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        try
        {
            categoryDao.create(category);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return new ResponseEntity<>(category, HttpStatus.NO_CONTENT);
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    @PutMapping("{id}")
    // add annotation to ensure that only an ADMIN can call this function
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id
        try
        {
          categoryDao.update(id, category);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable int id) {
        try {
            categoryDao.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.toString());
        }
    }
}
