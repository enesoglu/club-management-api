package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.dto.request.CreateTermRequest;
import com.cms.clubmanagementapi.dto.request.UpdateTermRequest;
import com.cms.clubmanagementapi.dto.response.TermDTO;
import com.cms.clubmanagementapi.repository.TermRepository;
import com.cms.clubmanagementapi.service.TermService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terms")
public class TermController {

    private final TermService termService;
    private final TermRepository termRepository;

    public TermController(TermService termService, TermRepository termRepository) {
        this.termService = termService;
        this.termRepository = termRepository;
    }

    // get all members
    @GetMapping
    public List<TermDTO> findAllTerms() {
        return termService.findAllTerms();
    }

    // get active term name
    @GetMapping("/get-active-term")
    public String getActiveTerm() {
        TermDTO activeTerm = termService.findActiveTerm();
        return activeTerm.getName();
    }

    // make a term active
    // e.g: PUT, http://localhost:8080/api/terms/5/set-active
    @PutMapping("/set-active/{id}")
    public ResponseEntity<TermDTO> setActiveTerm(@PathVariable Long id) {
        TermDTO activeTerm = termService.setActiveTerm(id);
        return ResponseEntity.ok(activeTerm);
    }

    @PostMapping("/create-term")
    public ResponseEntity<TermDTO> createTerm(@Valid @RequestBody CreateTermRequest term) {
        return ResponseEntity.ok(termService.createTerm(term));
    }

    @DeleteMapping("/delete-term/{id}")
    public ResponseEntity<Void> deleteTerm(@PathVariable Long id) {
        termRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No term by id:" + id));

        termService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // update term
    @PutMapping("/update-term/{id}")
    public ResponseEntity<TermDTO> updateTerm(@PathVariable long id, @RequestBody UpdateTermRequest termRequest){
        TermDTO updatedTerm = termService.updateTerm(id, termRequest);
        return ResponseEntity.ok(updatedTerm);
    }

}