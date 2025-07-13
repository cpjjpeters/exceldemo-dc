package com.dotcapital.exceldemodc.controller;

/* carlpeters created on 13/07/2025 inside the package - com.dotcapital.exceldemodc.controller */
import com.dotcapital.exceldemodc.entities.ProductIssueEntity;
import com.dotcapital.exceldemodc.services.ProductIssueService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pi")
public class ProductIssueController {

    private final ProductIssueService productIssueService;

    public ProductIssueController(ProductIssueService productIssueService) {
        this.productIssueService = productIssueService;
    }

    @GetMapping
    public ResponseEntity<List<ProductIssueEntity>> getAll() {
        log.debug("Fetching all product issues");
        List<ProductIssueEntity> issues = productIssueService.getAllProductIssues();
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductIssueEntity> getById(@PathVariable Long id) {
        log.debug("Fetching product issue with id {}", id);
        ProductIssueEntity issue = productIssueService.getProductIssueById(id);
        if (issue != null) {
            return new ResponseEntity<>(issue, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/create")
    public ResponseEntity<ProductIssueEntity> create(@RequestBody ProductIssueEntity productIssue) {
        log.debug("Creating product issue {}", productIssue);
        ProductIssueEntity created = productIssueService.createProductIssue(productIssue);
//        return new ResponseEntity<>(created, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductIssueEntity> update(@PathVariable Long id, @RequestBody ProductIssueEntity productIssue) {
        log.debug("Updating product issue with id {}", id);
        ProductIssueEntity updated = productIssueService.updateProductIssue(id, productIssue);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Deleting product issue with id {}", id);
        boolean deleted = productIssueService.deleteProductIssue(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
