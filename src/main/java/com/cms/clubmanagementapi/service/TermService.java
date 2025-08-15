package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.TermRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TermService {

    private final TermRepository termRepository;

    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    public Optional<Term> findByIsActiveTrue() {
        return termRepository.findByIsActiveTrue();
    }

    @Transactional
    public Term setActiveTerm(Long termId) {
        // is there an active (isActive = true) term?
        termRepository.findByIsActiveTrue().ifPresent(oldActiveTerm -> {
            // if so, make it false.
            oldActiveTerm.setActive(false);
            termRepository.save(oldActiveTerm);
        });

        // find the new term by id.
        Term newActiveTerm = termRepository.findById(termId)
                .orElseThrow(() -> new RuntimeException("Term not found with id: " + termId));

        // make the new term active (true).
        newActiveTerm.setActive(true);
        return termRepository.save(newActiveTerm);
    }
}