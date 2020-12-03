package com.covidtracker.covid19tracker.services;

import com.covidtracker.covid19tracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CovidDataServices {
    private static final long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
    private static String currentDate = simpleDateFormat.format(new Date(System.currentTimeMillis() - MILLIS_IN_DAY));

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports_us/" + currentDate + ".csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return this.allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for(CSVRecord record : records){
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province_State"));
            if(locationStat.getState().contains("Princess") || locationStat.getState().contains("Samoa")){
                continue;
            }
            double activeCases = Double.parseDouble(record.get("Active"));
            locationStat.setLatestTotalCases((int) activeCases);
            locationStat.setDeaths(Integer.parseInt(record.get("Deaths")));
            locationStat.setCaseFatalityRatio(Double.parseDouble(record.get("Case_Fatality_Ratio")));
            newStats.add(locationStat);
        }
        this.allStats = newStats;
    }
}
