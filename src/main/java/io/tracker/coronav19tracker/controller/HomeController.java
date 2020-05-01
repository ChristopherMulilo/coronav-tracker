package io.tracker.coronav19tracker.controller;

import io.tracker.coronav19tracker.Service.Covid19.DataService;
import io.tracker.coronav19tracker.models.LocationCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    DataService dataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationCases> currentData = dataService.getCurrentData();
        int totalCases = currentData.stream().mapToInt(stat -> stat.getCaseNumber()).sum();
        int totalNewCases = currentData.stream().mapToInt(stat -> stat.getDifferFromPrevDay()).sum();
        model.addAttribute("locationCases", dataService.getCurrentData());
        model.addAttribute("grandTotalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);

        return"home";
    }
}
