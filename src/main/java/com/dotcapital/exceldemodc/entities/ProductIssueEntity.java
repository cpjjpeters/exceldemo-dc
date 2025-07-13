package com.dotcapital.exceldemodc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* carlpeters created on 11/07/2025 inside the package - com.dotcapital.exceldemodc.entities */

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductIssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pi_id", nullable = false)
    private Long pId;
    @Column(name = "status")
    private String status;
    @Column(name = "productName")
    private String productName;
    @Column(name = "issueDescription")
    private String issueDescription;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    @Override
    public String toString() {
        return "ProductIssueEntity{" +
                "pId=" + pId +
                ", status='" + status + '\'' +
                ", productName='" + productName + '\'' +
                ", issueDescription='" + issueDescription + '\'' +
                '}';
    }

    public void setId(Long id) {
    }
}
