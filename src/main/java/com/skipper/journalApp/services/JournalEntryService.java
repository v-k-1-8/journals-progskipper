package com.skipper.journalApp.services;

import com.skipper.journalApp.entity.JournalEntry;
import com.skipper.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public void saveEntry(JournalEntry myEntry) {
        journalEntryRepository.save(myEntry);
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    public void updateJournalEntry(JournalEntry myEntry) {
        journalEntryRepository.save(myEntry);
    }
}
