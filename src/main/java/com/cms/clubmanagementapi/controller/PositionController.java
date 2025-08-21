package com.cms.clubmanagementapi.controller;

import com.cms.clubmanagementapi.dto.request.CreateMemberPositionRequest;
import com.cms.clubmanagementapi.dto.response.PositionDTO;
import com.cms.clubmanagementapi.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // get a member's all positions
    @GetMapping("/members/{memberId}/get-position")
    public List<PositionDTO> getPosition(@PathVariable Long memberId) {
        return positionService.getPositions(memberId);
    }

    // get a member's active positions
    @GetMapping("/members/{memberId}/get-active-position")
    public PositionDTO getActivePosition(@PathVariable Long memberId) {
        return positionService.getActivePosition(memberId);
    }

    // set a new position
    // e.g: POST, http://localhost:8080/api/members/1/set-position
    // BODY: { "team": "EXECUTIVE", "executiveTitle": "PRESIDENT"}
    @PostMapping("/members/{memberId}/set-position")
    public ResponseEntity<PositionDTO> addPosition(@PathVariable("memberId") Long memberId,
                                                   @RequestBody CreateMemberPositionRequest positionRequest)
    {
        PositionDTO newPositionDTO = positionService.addPositionToMember(memberId, positionRequest);
        return ResponseEntity.ok(newPositionDTO);
    }

    /* delete a position and set the member's position
    to the last position it had */
    @DeleteMapping("/members/delete-position/{positionId}")
    public String deletePosition(@PathVariable Long positionId) {
        return positionService.deletePosition(positionId);
    }
}
