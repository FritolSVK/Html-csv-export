package org.phoenixfox.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class VendorTable {

    private List<VendorData> vendorList;

    public VendorTable() {
        vendorList = new ArrayList<>();
    }

    public List<VendorData> getListOfVendors() {
        return vendorList;
    }

    public void setListOfVendors(List<VendorData> listOfVendors) {
        this.vendorList = listOfVendors;
    }

    public void add(VendorData vendorData) {
        this.vendorList.add(vendorData);
    }

    /**
     * Returns a VendorData object for the given vendor, or null if not found.
     */
    public VendorData getVendorData(String vendor) {
        for (VendorData vd : vendorList) {
            if (vd.getVendorName().equalsIgnoreCase(vendor)) {
                return vd;
            }
        }
        return null;
    }

    /**
     * Finds the row (0-based) containing information about a given vendor.
     * Returns -1 if vendor not found.
     */
    public int getVendorRowIndex(String vendor) {
        for (int i = 0; i < vendorList.size(); i++) {
            if (vendorList.get(i).getVendorName().equalsIgnoreCase(vendor)) {
                return i;
            }
        }
        return -1;
    }

    public void filterByQuarter(String year, String quarter) {
        vendorList = vendorList.stream()
                .filter(vendorData -> vendorData.getTimescale().startsWith(year))
                .filter(vendorData -> vendorData.getTimescale().endsWith(quarter)).toList();
    }

    public double getTotalUnits() {
        return vendorList.stream()
                .mapToDouble(VendorData::getUnits)
                .sum();
    }

    /**
     * Sorts the vendor list alphabetically by vendor name.
     */
    public void sortByVendorName() {
        vendorList.sort(Comparator.comparing(VendorData::getVendorName, String.CASE_INSENSITIVE_ORDER));
    }

    /**
     * Sorts the vendor list in descending order of units.
     */
    public void sortByUnitsDescending() {
        vendorList.sort(Comparator.comparingDouble(VendorData::getUnits).reversed());
    }

    /**
     * Filter by inputs and group by vendor
     *
     * @return sold units and share for vendor in a given quarter
     */
    public UnitsSoldAndShare getUnitsSoldAndShare(String vendor, String year, String quarter) {
        List<VendorData> filteredList = vendorList.stream()
                    .filter(vendorData -> vendorData.getVendorName().equals(vendor))
                    .filter(vendorData -> vendorData.getTimescale().startsWith(year))
                    .filter(vendorData -> vendorData.getTimescale().endsWith(quarter)).toList();

        double unitsSold = filteredList.stream().mapToDouble(VendorData::getUnits).sum();

        return new UnitsSoldAndShare(unitsSold, unitsSold / getTotalUnits());
    }

    public List<Integer> ascertainVendorInformation(String vendorFilter) {
        List<Integer> rows = new LinkedList<>();
        for (int i = 0; i < vendorList.size(); i++ ) {
            VendorData vendorData = vendorList.get(i);
            if (vendorData.getVendorName().equals(vendorFilter)) {
                rows.add(i);
            }
        }
        return rows.stream().map(r-> r+1).toList();
    }

    public record UnitsSoldAndShare(double unitsSold, double share) {}

    /**
     * Returns a Tab-Separated formatted string for pretty printing to terminal.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Country\tTimeScale\tVendor\tUnits\n");
        for (VendorData vd : vendorList) {
            sb.append(vd.toString()).append("\n");
        }
        double totalUnits = vendorList.stream().mapToDouble(VendorData::getUnits).sum();
        sb.append("Total\t")
                .append(String.format("%,.2f", totalUnits)).append("\t100%\n");
        return sb.toString();
    }
}
