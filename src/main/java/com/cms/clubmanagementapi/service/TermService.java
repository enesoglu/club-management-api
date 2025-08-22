package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.dto.request.CreateTermRequest;
import com.cms.clubmanagementapi.dto.response.TermDTO;
import com.cms.clubmanagementapi.mapper.TermMapper;
import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.TermRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class TermService {

    private final TermRepository termRepository;
    private final TermMapper termMapper;

    public TermService(TermRepository termRepository,  TermMapper termMapper) {
        this.termRepository = termRepository;
        this.termMapper = termMapper;
    }

    public TermDTO findActiveTerm() {
        Term term = termRepository.findByIsActiveTrue()
                .orElseThrow(()-> new EntityNotFoundException("Term not found"));
        return termMapper.toDTO(term);
    }

    }

    public void deleteById(@PathVariable Long id) { termRepository.deleteById(id); }

    @Transactional
    public TermDTO setActiveTerm(Long termId) {
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
        termRepository.save(newActiveTerm);
        return termMapper.toDTO(newActiveTerm);
    }
}