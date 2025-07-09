package com.dotcapital.exceldemodc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.html.HTMLBaseElement;

import java.io.IOException;
import java.time.LocalDate;

/* carlpeters created on 08/07/2025 inside the package - com.dotcapital.exceldemodc */

@RestController
@RequestMapping("/tba")
@Slf4j
public class TBAController {
    private final TBAService tbaService;

    public TBAController(TBAService tbaService) {
        this.tbaService = tbaService;
    }

    // Define your endpoints here
    // Example: @GetMapping("/example")
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
}
