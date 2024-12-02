package com.skipper.journalApp.controller;

import com.skipper.journalApp.entity.JournalEntry;
import com.skipper.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequestMapping("/journal")
@RestController
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
       try {
           List<JournalEntry> allEntries = journalEntryService.getAllEntries();
           if(allEntries != null && !allEntries.isEmpty())
               return new ResponseEntity<>(allEntries, HttpStatus.OK);
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       } catch(Exception e){
           throw new RuntimeException(e);
       }
       }

    @PostMapping
    public ResponseEntity<JournalEntry> addEntry(@RequestBody JournalEntry myEntry){
        try {
            if (myEntry == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            myEntry.setDateTime(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable ObjectId myId){
        try {
            Optional<JournalEntry> entry=journalEntryService.getJournalEntryById(myId);
            if(entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        try {
            JournalEntry oldEntry = getEntry(myId).getBody();
            if (oldEntry != null) {
                oldEntry.setTitle(newEntry.getTitle() != null && newEntry.getTitle() != "" ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContext(newEntry.getContext() != null && newEntry.getContext() != "" ? newEntry.getContext() : oldEntry.getContext());
                journalEntryService.updateJournalEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myId){
        journalEntryService.deleteJournalEntryById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
