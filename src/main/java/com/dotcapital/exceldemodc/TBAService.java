package com.dotcapital.exceldemodc;

import com.dotcapital.exceldemodc.entities.ProductIssueEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface TBAService {
    void generateF(LocalDate now);
    void createTBA(LocalDate now) throws IOException;
    void createSmallExcell(LocalDate now) throws IOException;

    List<ProductIssueEntity> getListOfProductIssues();
}
