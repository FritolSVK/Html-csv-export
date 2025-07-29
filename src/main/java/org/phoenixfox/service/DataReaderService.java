package org.phoenixfox.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.phoenixfox.entity.VendorData;
import org.phoenixfox.entity.VendorTable;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DataReaderService {

    public VendorTable loadFromCsv(String filename) throws IOException, CsvValidationException {
        VendorTable vendorTable = new VendorTable();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + filename);
            }
            try (CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String[] line;
                boolean headerSkipped = false;
                while ((line = reader.readNext()) != null) {
                    if (line.length < 4) continue; // must have 4 columns

                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }

                    String country = line[0].trim();
                    String quarter = line[1].trim();
                    String vendor = line[2].trim();
                    double units = Double.parseDouble(line[3].trim());
                    vendorTable.add(new VendorData(country, quarter, vendor, units));
                }
            }
        }

        return vendorTable;
    }
}
