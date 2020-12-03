package com.covidtracker.covid19tracker.controllers;

import com.covidtracker.covid19tracker.models.LocationStats;
import com.covidtracker.covid19tracker.services.CovidDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CovidDataServices covidDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> allStats = covidDataService.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int totalFatalities = allStats.stream().mapToInt(stat -> stat.getDeaths()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalFatalities", totalFatalities);
        return "home";
    }
}
