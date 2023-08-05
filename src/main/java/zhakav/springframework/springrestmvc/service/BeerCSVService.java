package zhakav.springframework.springrestmvc.service;

import zhakav.springframework.springrestmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCSVService {

    List<BeerCSVRecord> convertCSV(File csvFile);

}
