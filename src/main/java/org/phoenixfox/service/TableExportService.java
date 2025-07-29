package org.phoenixfox.service;

import org.phoenixfox.entity.VendorData;
import org.phoenixfox.entity.VendorTable;

public class TableExportService {

    /**
     * Exports the current table to an HTML table string
     * @param vendorTable the VendorTable containing vendor data
     * @return HTML table as a string.
     */
    public String exportToHtml(VendorTable vendorTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"1\">\n");
        sb.append("<tr><th>Vendor</th><th>Units</th><th>Share</th></tr>\n");



        for (VendorData vendor : vendorTable.getListOfVendors()) {

            sb.append("<tr>")
                    .append("<td>").append(vendor.getVendorName()).append("</td>")
                    .append("<td>").append(String.format("%.2f", vendor.getUnits())).append("</td>")
                    .append("<td>").append(String.format("%.1f%%", vendorTable.getShareForVendor(vendor.getVendorName()))).append("</td>")
                    .append("</tr>\n");
        }

        sb.append("<tr><td><b>Total</b></td><td><b>")
                .append(String.format("%.2f", vendorTable.getTotalUnits()))
                .append("</b></td><td><b>100%</b></td></tr>\n");

        sb.append("</table>\n");
        return sb.toString();
    }


    /**
     * Exports table to a CSV file.
     * <p>
     * Not implemented as per assignment. You would implement using a CSV writer.
     */
    public void exportToCsv(String targetFile) {
        // Implementation would write vendorList data as CSV rows to targetFile.
    }

    /**
     * Exports table to an Excel file.
     * <p>
     * Not implemented as per assignment. You would implement using a library like Apache POI.
     */
    public void exportToExcel(String targetFile) {
        // Implementation would write vendorList data to an Excel (.xlsx) file.
    }
}
