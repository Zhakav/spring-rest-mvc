package zhakav.springframework.springrestmvc.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import zhakav.springframework.springrestmvc.service.BeerService;

@Controller
@AllArgsConstructor
public class BeerController {
    private BeerService beerService;
}
