package io.tracker.coronav19tracker.models;

public class LocationCases {

    private String state;
    private String country;
    private int caseNumber;
    private int differFromPrevDay;

    public int getDifferFromPrevDay() {
        return differFromPrevDay;
    }

    public void setDifferFromPrevDay(int differFromPrevDay) {
        this.differFromPrevDay = differFromPrevDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    @Override
    public String toString() {
        return "LocationCases{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", caseNumber=" + caseNumber +
                '}';
    }
}
