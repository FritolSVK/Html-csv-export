package org.phoenixfox.entity;

public class VendorData {
    private String country;
    private String timescale;
    private String vendorName;
    private double units;

    public VendorData(String country, String quarter, String vendorName, double units) {
        this.country = country;
        this.timescale = quarter;
        this.vendorName = vendorName;
        this.units = units;
    }

    // getters and setters
    public String getCountry() { return country; }
    public String getTimescale() { return timescale; }
    public String getVendorName() { return vendorName; }
    public double getUnits() { return units; }

    public void setCountry(String country) { this.country = country; }
    public void setTimescale(String timescale) { this.timescale = timescale; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public void setUnits(double units) { this.units = units; }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%.6f", country, timescale, vendorName, units);
    }
}

