package com.dotcapital.exceldemodc;

import com.dotcapital.exceldemodc.entities.ProductIssueEntity;
import com.dotcapital.exceldemodc.repositories.ProductIssueRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TBAServiceImpl implements TBAService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TBAServiceImpl.class);


    // or your desired value
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final String DIRECTORY_NAME = "tba";
    private static final String FILE_NAME = "-TBAs and CPs Request.xlsm";
    private static final String TEMPLATE_FILE_NAME = "TBAs and CPs Request Template.xlsm";

    private static final String INSTITUTIONAL_SHEET = "Instit - Corp";
    private static final String PUBLIC_SHEET = "Publ - SemiPubl";

    private static final String XLSM_CONTENT_TYPE = "application/vnd.ms-excel.sheet.macroEnabled.12";
    private final ProductIssueRepository productIssueRepository;

    public TBAServiceImpl(ProductIssueRepository productIssueRepository) {
        this.productIssueRepository = productIssueRepository;
    }

//    private final StorageService storageService;
//    private final InternalCodeService internalCodeService;
//
//    public TBAServiceImpl(StorageService storageService,
//                          InternalCodeService internalCodeService) {
//        this.storageService = storageService;
//        this.internalCodeService = internalCodeService;
//    }

    @Override
    public void saveProductIssuesToExcelFile(List<ProductIssueEntity> allProductIssues, String filePath) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Product Issues");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Product Name");
            header.createCell(2).setCellValue("Issue Description");
            header.createCell(3).setCellValue("Status");
            int rowIdx = 1;
            for (ProductIssueEntity issue : allProductIssues) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(issue.getpId());
                row.createCell(1).setCellValue(issue.getProductName());
                row.createCell(2).setCellValue(issue.getIssueDescription());
                row.createCell(3).setCellValue(issue.getStatus());
            }
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
    @Override
    public void generateF(LocalDate date) {
        LOGGER.info("Generating TBA from file for date: {}", date);
        String mainDirectoryTemplate = "TBAs and CPs Request Template.xlsm";
        String outputDirectory = DIRECTORY_NAME;
        String outputFileName = date.format(DATE_FILE_NAME_FORMATTER) + FILE_NAME;

        // Ensure the directory exists
        java.io.File dir = new java.io.File(outputDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        LOGGER.info("Generating {} in {}", outputFileName, outputDirectory);
        long start = System.currentTimeMillis();
        try (InputStream input = new FileInputStream(mainDirectoryTemplate)) {
            try (XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(input)) {
                Sheet s = wb.getSheet(INSTITUTIONAL_SHEET);
                LOGGER.info("Value at 2;0 {}", s.getRow(2).getCell(0).getStringCellValue());
                LOGGER.info("Value at 2;2 {}", s.getRow(2).getCell(2).getStringCellValue());
                LOGGER.info("Value at 2;8 {}", s.getRow(2).getCell(8).getStringCellValue());
                LOGGER.info("Value at 3;8 {}", s.getRow(3).getCell(8).getStringCellValue());

                Row r = s.getRow(0);
                Cell c = r.getCell(4);
                String formattedDate = date.format(DATE_FORMATTER);
                c.setCellValue(formattedDate);
                long end = System.currentTimeMillis();
                LOGGER.info("Step took {} ms", (end - start));

                start = System.currentTimeMillis();
                // Write to file in the directory
                try (FileOutputStream fileOut = new FileOutputStream(new java.io.File(dir, outputFileName))) {
                    wb.write(fileOut);
                } catch (Exception e) {
                    LOGGER.error("Could not write to file", e);
                    throw new RuntimeException("Failed to write output", e);
                }
                end = System.currentTimeMillis();
                LOGGER.info("Write step took {} ms", (end - start));

            } catch (Exception e) {
                LOGGER.error("Could not create XSSFWorkbook", e);
                throw new RuntimeException("Failed to process workbook", e);
            }
        } catch (Exception e) {
            LOGGER.error("Could not open file from main directory: {}", mainDirectoryTemplate, e);
            throw new RuntimeException("Failed to open template file", e);
        }
    }

    @Override
    public void createTBA(LocalDate now) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        LOGGER.info("Creating {} in {}");
        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 8000);
        sheet.setColumnWidth(3, 4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Email");
        headerCell.setCellStyle(headerStyle);

//
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(20);
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("John.Smith@gmail.com");
        cell.setCellStyle(style);
//

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";
        LOGGER.info("Creating {} in {}", fileLocation, path);
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    @Override
    public void createSmallExcell(LocalDate now) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        LOGGER.info("Creating small excel file");
        Sheet sheet = wb.createSheet("Small Sheet");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Age");
        headerRow.createCell(2).setCellValue("Department");

        XSSFRow dataRow = (XSSFRow) sheet.createRow(1);
        dataRow.createCell(0).setCellValue("Carl");
        dataRow.createCell(1).setCellValue("61");
        dataRow.createCell(2).setCellValue("IT Department");

        Sheet sheet2 = wb.createSheet("Sheet2");
        int rowCount1 = sheet.getPhysicalNumberOfRows();
        int rowCount2 = sheet2.getPhysicalNumberOfRows();
        LOGGER.info("Row count in Sheet1: {}, Row count in Sheet2: {}", rowCount1, rowCount2);
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "employee.xlsx";
        LOGGER.info("Creating {} in {}", fileLocation, path);
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        wb.write(outputStream);
        wb.close();


    }

    @Override
    public List<ProductIssueEntity> getListOfProductIssues() {

        List<ProductIssueEntity> productIssueEntityList = productIssueRepository.findByStatus("ACTIVE");
        return productIssueEntityList;
    }

    @Override
    public byte[] exportProductIssuesToExcel(List<ProductIssueEntity> allProductIssues) {
        return new byte[0];
    }

    public List<ProductIssueEntity> getAllProductIssues() {

        List<ProductIssueEntity> productIssueEntityList = productIssueRepository.findAll();
        return productIssueEntityList;
    }
//    @Override
//    public void generate(LocalDate date) {
//        LOGGER.info("Generating TBA for date: {}", date);
//        try (InputStream input = storageService.getObject(DIRECTORY_NAME, TEMPLATE_FILE_NAME)){
//            try (XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(input)) {
//                Sheet s = wb.getSheet(INSTITUTIONAL_SHEET);
//                LOGGER.info("Value at 2;0 {}", s.getRow(2).getCell(0).getStringCellValue());
//                Row r = s.getRow(0);
//                Cell c = r.getCell(4);
//                String formattedDate = date.format(DATE_FORMATTER);
//                c.setCellValue(formattedDate);
//
//                try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//                    wb.write(outputStream);
//                    storageService.putObject(DIRECTORY_NAME, date.format(DATE_FILE_NAME_FORMATTER) + FILE_NAME, outputStream.toByteArray(), XLSM_CONTENT_TYPE);
//                } catch (Exception e) {
//                    LOGGER.error("Could not write to OutputStream", e);
//                }
//            } catch (Exception e) {
//                LOGGER.error("Could not create XSSFWorkbook", e);
//            }
//        } catch(Exception e) {
//            LOGGER.error("Could not open file", e);
//        }
//    }
}
