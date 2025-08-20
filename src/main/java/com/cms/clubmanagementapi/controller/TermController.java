package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.TermRepository;
import com.cms.clubmanagementapi.service.TermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/api/terms")
public class TermController {

    private final TermService termService;
    private final TermRepository termRepository;

    public TermController(TermService termService, TermRepository termRepository) {
        this.termService = termService;
        this.termRepository = termRepository;
    }

    // get active term name
    @GetMapping("/get-active-term")
    public String getActiveTerm() {
        Term activeTerm = termService.findByIsActiveTrue()
                .orElseThrow(()-> new RuntimeException("No Active Term"));
        return activeTerm.getName();
    }

    // make a term active
    // e.g: PUT, http://localhost:8080/api/terms/5/set-active
    @PostMapping("/{id}/set-active")
    public ResponseEntity<Term> setActiveTerm(@PathVariable Long id) {
        Term activeTerm = termService.setActiveTerm(id);
        return ResponseEntity.ok(activeTerm);
    }

    @DeleteMapping
    public String deleteTerm(@PathVariable Long id) {
        Optional<Term> term = Optional.ofNullable(termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Term by id:" + id)));

        String msg = "term" + term.get() + "deleted.";

        termService.DeleteById(id);

        return msg;
    }
}