package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.dto.CreateClubMemberPosition;
import com.cms.clubmanagementapi.dto.PositionDTO;
import com.cms.clubmanagementapi.mapper.PositionMapper;
import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.role.Position;
import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;
import com.cms.clubmanagementapi.repository.PositionRepository;
import com.cms.clubmanagementapi.repository.TermRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PositionService {

    private final ClubMemberRepository clubMemberRepository;
    private final PositionRepository positionRepository;
    private final TermRepository termRepository;
    private final PositionMapper positionMapper;

    public PositionService(ClubMemberRepository clubMemberRepository,
                           PositionRepository positionRepository,
                           TermRepository termRepository,
                           PositionMapper positionMapper) {

        this.clubMemberRepository = clubMemberRepository;
        this.positionRepository = positionRepository;
        this.termRepository = termRepository;
        this.positionMapper = positionMapper;
    }

    // find a member's all positions
    @Transactional(readOnly = true)
    public List<PositionDTO> getPositions(@PathVariable Long memberId) {
        List<Position> positions = positionRepository.findAllByMemberIdWithTerm(memberId);
        return positionMapper.toDTOList(positions);
    }

    // find a member's active position
    public PositionDTO getActivePosition(@PathVariable Long memberId){
        Position activePosition = positionRepository.findActiveByMemberIdWithTerm(memberId);
        return positionMapper.toDTO(activePosition);
    }

    @Transactional
    public PositionDTO addPositionToMember (Long memberId, CreateClubMemberPosition positionRequest){

        // find the member by id
        ClubMember member = clubMemberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("member not found"));

        // find the member's all positions and set to passive (false)
        List<Position> activePositions = positionRepository.findAllByMemberIdWithTerm(member.getId());
        for (Position oldPosition : activePositions){
            oldPosition.setActive(false);
            oldPosition.setEndDate(LocalDate.now());
        }
        positionRepository.saveAll(activePositions);

        // find the active term
        Term activeTerm = termRepository.findByIsActiveTrue()
                .orElseThrow(()-> new IllegalStateException("No Active Term"));

        // create a new member entity from coming DTO
        Position newPosition = new Position();
        newPosition.setMember(member);
        newPosition.setTeam(positionRequest.getTeam());
        newPosition.setExecutiveTitle(positionRequest.getExecutiveTitle()); // if exists
        newPosition.setCrewCommittee(positionRequest.getCrewCommittee());   // if exists
        newPosition.setTerm(activeTerm);
        newPosition.setStartDate(LocalDate.now());
        newPosition.setActive(true);

        Position savedPosition = positionRepository.save(newPosition);

        // save new position to the database
        return positionMapper.toDTO(savedPosition);
    }

}
