package com.example.personalFinanceTracker.service;



import com.example.personalFinanceTracker.model.EntryType;
import com.example.personalFinanceTracker.model.FinanceEntry;
import com.example.personalFinanceTracker.repository.FinanceEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class FinanceEntryService {

    private final FinanceEntryRepository repository;

    public FinanceEntryService(FinanceEntryRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public FinanceEntry addEntry(FinanceEntry entry) {
        return repository.save(entry);
    }

    // READ + FILTER
    public List<FinanceEntry> getEntries(
            EntryType type,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (type != null && startDate != null && endDate != null) {
            return repository.findByTypeAndDateBetween(type, startDate, endDate);
        }

        if (type != null) {
            return repository.findByType(type);
        }

        if (category != null) {
            return repository.findByCategory(category);
        }

        if (startDate != null && endDate != null) {
            return repository.findByDateBetween(startDate, endDate);
        }

        return repository.findAll();
    }

    // READ BY ID
    public FinanceEntry getEntryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Finance entry not found with id: " + id));
    }

    // UPDATE
    public FinanceEntry updateEntry(Long id, FinanceEntry updatedEntry) {
        FinanceEntry existing = getEntryById(id);

        existing.setAmount(updatedEntry.getAmount());
        existing.setType(updatedEntry.getType());
        existing.setCategory(updatedEntry.getCategory());
        existing.setDate(updatedEntry.getDate());
        existing.setDescription(updatedEntry.getDescription());

        return repository.save(existing);
    }

    // DELETE
    public void deleteEntry(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Finance entry not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public Map<String, Long> getSummary() {

        List<FinanceEntry> incomeEntries =
                repository.findByType(EntryType.INCOME);

        List<FinanceEntry> expenseEntries =
                repository.findByType(EntryType.EXPENSE);

        long totalIncome = incomeEntries.stream()
                .mapToLong(FinanceEntry::getAmount)
                .sum();

        long totalExpense = expenseEntries.stream()
                .mapToLong(FinanceEntry::getAmount)
                .sum();

        long balance = totalIncome - totalExpense;

        Map<String, Long> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("balance", balance);

        return summary;
    }

}
