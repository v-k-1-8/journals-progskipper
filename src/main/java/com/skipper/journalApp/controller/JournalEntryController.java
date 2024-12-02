package com.skipper.journalApp.controller;

import com.skipper.journalApp.entity.JournalEntry;
import com.skipper.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/journal")
@RestController
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAllEntries();
    }

    @PostMapping
    public boolean addEntry(@RequestBody JournalEntry myEntry){
        if(myEntry==null)return false;
        myEntry.setDateTime(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return true;
    }
    @GetMapping("/id/{myId}")
    public JournalEntry getEntry(@PathVariable ObjectId myId){
        return journalEntryService.getJournalEntryById(myId);
    }
    @PutMapping("/id/{myId}")
    public JournalEntry updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry=getEntry(myId);
        if(oldEntry!=null){
            oldEntry.setTitle(newEntry.getTitle()!=null && newEntry.getTitle()!=""?newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContext(newEntry.getContext()!=null && newEntry.getContext()!=""?newEntry.getContext(): oldEntry.getContext());
        }
        journalEntryService.updateJournalEntry(oldEntry);
        return oldEntry;
    }
    @DeleteMapping("/id/{myId}")
    public boolean deleteEntry(@PathVariable ObjectId myId){
        journalEntryService.deleteJournalEntryById(myId);
        return true;
    }
}
