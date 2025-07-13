package com.dotcapital.exceldemodc.services;

import com.dotcapital.exceldemodc.entities.ProductIssueEntity;

import java.io.IOException;
import java.util.List;

public interface ProductIssueService {


    /**
     * Retrieves a list of product issues.
     *
     * @return a list of product issues
     */
    List<ProductIssueEntity> getListOfProductIssues(String status);

    /**
     * Retrieves a list of product issues.
     *
     * @return a list of product issues
     */
    List<ProductIssueEntity> getAllProductIssues();
    /**
     * Creates a new product issue.
     *
     * @param productIssue the product issue to create
     * @return the created product issue
     */
    ProductIssueEntity createProductIssue(ProductIssueEntity productIssue);

    /**
     * Updates an existing product issue.
     *
     * @param productIssue the product issue to update
     * @return the updated product issue
     */
    ProductIssueEntity updateProductIssue(Long id,ProductIssueEntity productIssue);

    ProductIssueEntity getProductIssueById(Long id);

    boolean deleteProductIssue(Long id);

    void exportProductIssuesToExcelFile(List<ProductIssueEntity> allProductIssues, String filePath) throws IOException;
}
