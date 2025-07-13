package com.dotcapital.exceldemodc.services;

import com.dotcapital.exceldemodc.entities.ProductIssueEntity;
import com.dotcapital.exceldemodc.repositories.ProductIssueRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/* carlpeters created on 13/07/2025 inside the package - com.dotcapital.exceldemodc.services */
@Slf4j
//@AllArgsConstructor
@Service
public class ProductIssueServiceImpl implements ProductIssueService {
    private final ProductIssueRepository productIssueRepository;

    public ProductIssueServiceImpl(ProductIssueRepository productIssueRepository) {
        this.productIssueRepository = productIssueRepository;
    }

    @Override
    public List<ProductIssueEntity> getListOfProductIssues(String status) {
        log.debug("getListOfProductIssues");
        return this.productIssueRepository.findByStatus(status);
    }
    @Override
     public List<ProductIssueEntity> getAllProductIssues() {
        log.debug("getAllProductIssues");
        return this.productIssueRepository.findAll();
    }

    @Override
    public ProductIssueEntity createProductIssue(ProductIssueEntity productIssue) {
        log.debug("createProductIssue {}", productIssue);
        return this.productIssueRepository.save(productIssue);
    }

    @Override
    public ProductIssueEntity updateProductIssue(Long id, ProductIssueEntity productIssue) {
        log.debug("updateProductIssue");
        Optional<ProductIssueEntity> existing = this.productIssueRepository.findById(id);
        if (existing.isPresent()) {
            productIssue.setpId(id);
            return this.productIssueRepository.save(productIssue);
        }
        return null;
    }

    @Override
    public ProductIssueEntity getProductIssueById(Long id) {
        log.debug("getProductIssueById");
        return this.productIssueRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteProductIssue(Long id) {
        log.debug("deleteProductIssue");
        if (this.productIssueRepository.existsById(id)) {
            this.productIssueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void exportProductIssuesToExcelFile(List<ProductIssueEntity> allProductIssues, String filePath) throws IOException {

    }
}
