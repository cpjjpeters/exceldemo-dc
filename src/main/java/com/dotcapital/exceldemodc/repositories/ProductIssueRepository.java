package com.dotcapital.exceldemodc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dotcapital.exceldemodc.entities.ProductIssueEntity;

import java.util.List;

public interface ProductIssueRepository extends JpaRepository<ProductIssueEntity, Long> {
    // Define custom query methods if needed
    // For example, find by status, priority, etc.
     List<ProductIssueEntity> findByStatus(String status);

}
