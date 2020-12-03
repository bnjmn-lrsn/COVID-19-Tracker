package com.covidtracker.covid19tracker.models;

public class LocationStats {

    private String state;
    private int deaths;
    private double caseFatalityRatio;
    private int latestTotalCases;

    public String getState() {
        return this.state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setDeaths(final int deaths) {
        this.deaths = deaths;
    }

    public double getCaseFatalityRatio() {
        return this.caseFatalityRatio;
    }

    public void setCaseFatalityRatio(final double caseFatalityRatio) {
        this.caseFatalityRatio = caseFatalityRatio;
    }

    public int getLatestTotalCases() {
        return this.latestTotalCases;
    }

    public void setLatestTotalCases(final int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
