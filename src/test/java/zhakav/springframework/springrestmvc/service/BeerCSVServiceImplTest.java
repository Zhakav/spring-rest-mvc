package zhakav.springframework.springrestmvc.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import zhakav.springframework.springrestmvc.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class BeerCSVServiceImplTest {

    BeerCSVService beerCSVService=new BeerCSVServiceImpl();

    @Test
    public void convertCSV() throws FileNotFoundException {

        File file= ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> csvRecordList=beerCSVService.convertCSV(file);

        System.out.println("SIZE : " + csvRecordList.size());

        assertThat(csvRecordList.size()).isGreaterThan(1);

    }

}