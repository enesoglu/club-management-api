package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.dto.CreateClubMemberPosition;
import com.cms.clubmanagementapi.dto.PositionDTO;
import com.cms.clubmanagementapi.repository.PositionRepository;
import com.cms.clubmanagementapi.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PositionController {

    private final PositionRepository positionRepository;
    private final PositionService positionService;

    public PositionController(PositionRepository positionRepository, PositionService positionService) {
        this.positionRepository = positionRepository;
        this.positionService = positionService;
    }

    // set a new position
    // e.g: POST, http://localhost:8080/api/members/1/positions
    // BODY: { "team": "EXECUTIVE", "executiveTitle": "PRESIDENT"}
    @PostMapping("/members/{memberId}/positions")
    public ResponseEntity<PositionDTO> addPosition(@PathVariable("memberId") Long memberId,
                                                   @RequestBody CreateClubMemberPosition positionRequest) {
    PositionDTO newPositionDTO = positionService.addPositionToMember(memberId, positionRequest);

    return ResponseEntity.ok(newPositionDTO);
    }

}
