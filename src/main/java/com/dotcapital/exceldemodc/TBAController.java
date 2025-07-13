package com.dotcapital.exceldemodc;

import com.dotcapital.exceldemodc.entities.ProductIssueEntity;
import com.dotcapital.exceldemodc.services.ProductIssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.html.HTMLBaseElement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/* carlpeters created on 08/07/2025 inside the package - com.dotcapital.exceldemodc */

@RestController
@RequestMapping("/tba")
@Slf4j
public class TBAController {
    private final TBAService tbaService;
    private final ProductIssueService productIssueService;

    public TBAController(TBAService tbaService, ProductIssueService productIssueService) {
        this.tbaService = tbaService;
        this.productIssueService = productIssueService;
    }

    @GetMapping("/getListOfProductIssues")
    public ResponseEntity<List<ProductIssueEntity>> getListOfProductIssues() {
        log.info("Fetching list of product issues, status OPEN");
        // Assuming you have a service to fetch product issues
        List<ProductIssueEntity> productIssues = productIssueService.getListOfProductIssues("OPEN");
        return new ResponseEntity<>(productIssues, HttpStatus.OK);
    }
    @GetMapping("/getAllProductIssues")
    public ResponseEntity<List<ProductIssueEntity>> getAllProductIssues(){
        log.info("Fetching list of product issues");
        List<ProductIssueEntity> allProductIssues = productIssueService.getAllProductIssues();
        return new ResponseEntity<List<ProductIssueEntity>>(allProductIssues, HttpStatus.OK);
    }

    @PostMapping //(produces = MediaType.APPLICATION_XML_VALUE)
     public ResponseEntity<Void> generate() {
        log.info("Generate TBA from template");
        tbaService.generateF(LocalDate.now());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping ("/create")
    public ResponseEntity<Void> create() throws IOException {
        log.info("Creating TBA from scratch");
        tbaService.createTBA(LocalDate.now());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping ("/employee")
    public ResponseEntity<Void> employee() throws IOException {
        log.info("Creating employeefile from scratch");
        tbaService.createSmallExcell(LocalDate.now());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/exportProductIssues", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> exportProductIssuesToExcel() throws IOException {
        log.info("Exporting all product issues to Excel");
        List<ProductIssueEntity> allProductIssues = productIssueService.getAllProductIssues();
        byte[] excelData = tbaService.exportProductIssuesToExcel(allProductIssues);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=product_issues.xlsx")
                .body(excelData);
    }

    @PostMapping("/saveProductIssuesExcel")
    public ResponseEntity<Void> saveProductIssuesExcel() throws IOException {
        log.info("Saving all product issues to Excel file");
        List<ProductIssueEntity> allProductIssues = productIssueService.getAllProductIssues();
        String filePath = "product_issues_" + LocalDate.now() + ".xlsx";
        tbaService.saveProductIssuesToExcelFile(allProductIssues, filePath);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
