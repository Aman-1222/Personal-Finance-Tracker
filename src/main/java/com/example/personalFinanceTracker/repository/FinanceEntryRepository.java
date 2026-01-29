package com.example.personalFinanceTracker.repository;


import com.example.personalFinanceTracker.model.EntryType;
import com.example.personalFinanceTracker.model.FinanceEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinanceEntryRepository extends JpaRepository<FinanceEntry, Long> {

    // Get all income or all expense entries
    List<FinanceEntry> findByType(EntryType type);

    // Filter by category
    List<FinanceEntry> findByCategory(String category);

    // Filter by date range
    List<FinanceEntry> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Filter by type + date range (for monthly/yearly summary)
    List<FinanceEntry> findByTypeAndDateBetween(
            EntryType type,
            LocalDate startDate,
            LocalDate endDate
    );
}
