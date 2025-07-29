package org.phoenixfox.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendorTable {

    private static final Logger logger = Logger.getLogger(VendorTable.class.getName());

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

    /**
     * Gets share for vendor with name
     */
    public double getShareForVendor(String vendorName) {
        double totalUnits = getTotalUnits();
        VendorData vendorData = getVendorData(vendorName);
        if (vendorData == null) {
            logger.log(Level.WARNING, "Vendor not found to calculate total units");
            return -1;
        }
        return totalUnits > 0 ? (vendorData.getUnits() / totalUnits) * 100 : 0;
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
