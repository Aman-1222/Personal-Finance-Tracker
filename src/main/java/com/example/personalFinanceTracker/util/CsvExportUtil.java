package com.example.personalFinanceTracker.util;

import com.example.personalFinanceTracker.model.FinanceEntry;

import java.util.List;

public class CsvExportUtil {

    public static String convertToCsv(List<FinanceEntry> entries) {

        StringBuilder sb = new StringBuilder();

        // CSV Header
        sb.append("Id,Amount,Type,Category,Date,Description\n");

        // CSV Rows
        for (FinanceEntry entry : entries) {
            sb.append(entry.getId()).append(",");
            sb.append(entry.getAmount()).append(",");
            sb.append(entry.getType()).append(",");
            sb.append(entry.getCategory()).append(",");
            sb.append(entry.getDate()).append(",");
            sb.append(entry.getDescription() != null ? entry.getDescription() : "")
                    .append("\n");
        }

        return sb.toString();
    }
}