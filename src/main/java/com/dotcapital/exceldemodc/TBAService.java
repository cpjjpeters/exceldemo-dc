package com.dotcapital.exceldemodc;

import java.io.IOException;
import java.time.LocalDate;

public interface TBAService {
    void generateF(LocalDate now);
    void createTBA(LocalDate now) throws IOException;

}
