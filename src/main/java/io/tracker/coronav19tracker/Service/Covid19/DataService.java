package io.tracker.coronav19tracker.Service.Covid19;

import io.tracker.coronav19tracker.models.LocationCases;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    public static String covid19_dataUrl ="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationCases> currentData = new ArrayList<>();

    public List<LocationCases> getCurrentData() {
        return currentData;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getCovid19Data() throws IOException, InterruptedException {
        List<LocationCases> updatedData = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(covid19_dataUrl))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());
        StringReader csvReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(csvReader);
        for (CSVRecord record : records) {
            LocationCases locationCases = new LocationCases();
            locationCases.setState(record.get("Province/State"));
            locationCases.setCountry(record.get("Country/Region"));// in green are headers names from the source file
           int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationCases.setCaseNumber(latestCases);

            locationCases.setDifferFromPrevDay(latestCases - prevDayCases);

            updatedData.add(locationCases);


        }

        this.currentData = updatedData;
    }
}

