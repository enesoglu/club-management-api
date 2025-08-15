package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.service.TermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/terms")
public class TermController {

    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    // make a term active
    // e.g: PUT, http://localhost:8080/api/terms/5/set-active
    @PostMapping("/{id}/set-active")
    public ResponseEntity<Term> setActiveTerm(@PathVariable Long id) {
        Term activeTerm = termService.setActiveTerm(id);
        return ResponseEntity.ok(activeTerm);
    }
}