package com.example.personalFinanceTracker.service;



import com.example.personalFinanceTracker.model.FinanceEntry;
import com.example.personalFinanceTracker.repository.FinanceEntryRepository;
import org.springframework.stereotype.Service;


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


}
