package com.example.personalFinanceTracker.controller;


//import com.yourname.financetracker.model.EntryType;
//import com.yourname.financetracker.model.FinanceEntry;
//import com.yourname.financetracker.service.FinanceEntryService;
import com.example.personalFinanceTracker.model.EntryType;
import com.example.personalFinanceTracker.model.FinanceEntry;
import com.example.personalFinanceTracker.service.FinanceEntryService;
import com.example.personalFinanceTracker.util.CsvExportUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/entries")
public class FinanceEntryController {

    private final FinanceEntryService service;

    public FinanceEntryController(FinanceEntryService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<FinanceEntry> createEntry(@RequestBody FinanceEntry entry) {
        FinanceEntry savedEntry = service.addEntry(entry);
        return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
    }

    // READ ALL (with filters)
    @GetMapping
    public ResponseEntity<List<FinanceEntry>> getAllEntries(
            @RequestParam(required = false) EntryType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return ResponseEntity.ok(
                service.getEntries(type, category, startDate, endDate)
        );
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<FinanceEntry> getEntryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEntryById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FinanceEntry> updateEntry(
            @PathVariable Long id,
            @RequestBody FinanceEntry entry
    ) {
        return ResponseEntity.ok(service.updateEntry(id, entry));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return ResponseEntity.ok("Entry deleted successfully");
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Long>> getSummary() {
        return ResponseEntity.ok(service.getSummary());
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToCsv() {

        List<FinanceEntry> entries = service.getEntries(null, null, null, null);

        String csvData = CsvExportUtil.convertToCsv(entries);

        byte[] csvBytes = csvData.getBytes();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=finance_entries.csv")
                .header("Content-Type", "text/csv")
                .body(csvBytes);
    }
}
