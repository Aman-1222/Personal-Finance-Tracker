package com.example.personalFinanceTracker.model;

import jakarta.persistence.*;



import java.time.LocalDate;

@Entity
@Table(name = "finance_entries")
public class FinanceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long amount;   // 👈 simple long

    @Enumerated(EnumType.STRING)
    private EntryType type;


    private String category;


    private LocalDate date;

    private String description;

    public FinanceEntry() {}

    public FinanceEntry(long amount, EntryType type,
                        String category, LocalDate date,
                        String description) {
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}