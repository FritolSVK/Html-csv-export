package org.phoenixfox;

import org.phoenixfox.entity.VendorTable;
import org.phoenixfox.service.DataReaderService;
import org.phoenixfox.entity.VendorData;
import org.phoenixfox.service.TableExportService;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String csvFilePath = "IDC-data.csv";
        VendorTable table;

        try (Scanner scanner = new Scanner(System.in)) {
            DataReaderService dataReaderService = new DataReaderService();
            TableExportService tableExportService = new TableExportService();
            table = dataReaderService.loadFromCsv(csvFilePath);
            System.out.println("Data loaded successfully.");

            boolean exit = false;
            while (!exit) {
                System.out.println("\nChoose an option:");
                System.out.println("1. Print current table");
                System.out.println("2. Query vendor data");
                System.out.println("3. Sort alphabetically by vendor");
                System.out.println("4. Sort by units descending");
                System.out.println("5. Export to HTML");
                System.out.println("0. Exit");

                System.out.print("Enter choice (0-5): ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.println("\nCurrent Table:");
                        System.out.println(table.toString());
                        break;

                    case "2":
                        System.out.print("Enter vendor name to query: ");
                        String vendor = scanner.nextLine().trim();
                        VendorData vendorData = table.getVendorData(vendor);
                        if (vendorData != null) {
                            System.out.printf("%s sold %.2f units\n",
                                    vendorData.getVendorName(), vendorData.getUnits());
                            System.out.println("Row index (0-based): " + table.getVendorRowIndex(vendor));
                        } else {
                            System.out.println("Vendor not found.");
                        }
                        break;

                    case "3":
                        table.sortByVendorName();
                        System.out.println("Sorted alphabetically by vendor.");
                        break;

                    case "4":
                        table.sortByUnitsDescending();
                        System.out.println("Sorted by units in descending order.");
                        break;

                    case "5":
                        System.out.print("Input name for the html file:");
                        String filename = scanner.nextLine().trim();
                        if (!filename.endsWith(".html")) {
                            filename = filename + ".html";
                        }
                        String html = tableExportService.exportToHtml(table);
                        try (FileWriter writer = new FileWriter(filename)) {
                            writer.write(html);
                            System.out.println("Data exported to " + filename);
                            if (Desktop.isDesktopSupported()) {

                                System.out.print("Would you like to open the file?(Y/N)");
                                String openFile = scanner.nextLine().trim();
                                if (openFile.equalsIgnoreCase("y")) {
                                    File htmlFile = new File(filename);
                                    Desktop.getDesktop().browse(htmlFile.toURI());
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Failed to write HTML file: " + e.getMessage());
                        }
                        break;

                    case "0":
                        System.out.println("Exiting...");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid option. Please enter a number from 1 to 6.");
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to load or process data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}