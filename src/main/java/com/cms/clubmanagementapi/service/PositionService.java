package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.dto.request.CreateMemberPositionRequest;
import com.cms.clubmanagementapi.dto.response.PositionDTO;
import com.cms.clubmanagementapi.mapper.PositionMapper;
import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.role.Position;
import com.cms.clubmanagementapi.model.role.Team;
import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;
import com.cms.clubmanagementapi.repository.PositionRepository;
import com.cms.clubmanagementapi.repository.TermRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


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

    public CreateMemberPositionRequest createDefaultMemberPosition() {
        CreateMemberPositionRequest defaultPosition = new CreateMemberPositionRequest();
        defaultPosition.setTeam(Team.MEMBER);               // default position member

        return defaultPosition;
    }

    public Position createPositionForNewMember(ClubMember newMember, CreateMemberPositionRequest positionRequest, Term activeTerm) {

        Position newPosition = new Position();
        newPosition.setMember(newMember);
        newPosition.setTerm(activeTerm);
        newPosition.setStartDate(LocalDate.now());
        newPosition.setActive(true);

        final Set<Team> validTeamsForCopy = Set.of(
                Team.CREW, Team.EXECUTIVE, Team.VETERAN, Team.SUPERVISORY
        );

        if (positionRequest != null && validTeamsForCopy.contains(positionRequest.getTeam())) {
            newPosition.setTeam(positionRequest.getTeam());
            newPosition.setCrewCommittee(positionRequest.getCrewCommittee());
            newPosition.setExecutiveTitle(positionRequest.getExecutiveTitle());
        } else {
            // if position is null, empty or "Team.MEMBER" position will set to "MEMBER"
            newPosition.setTeam(Team.MEMBER);
        }

        return newPosition;
    }

    public Position createDefaultPositionForMember(ClubMember member, Term term) {
        Position position = new Position();
        position.setMember(member);
        position.setTerm(term);
        position.setTeam(Team.MEMBER);
        position.setStartDate(LocalDate.now());
        position.setActive(true);
        return position;
    }

    @Transactional
    public PositionDTO addPositionToMember (Long memberId, CreateMemberPositionRequest positionRequest){

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

    @Transactional
    public String deletePosition(@PathVariable long positionId){

        Position positionToDelete = positionRepository.findById(positionId)
                .orElseThrow(() -> new EntityNotFoundException("position not found with id: " + positionId));

        // get member from position
        ClubMember member = positionToDelete.getMember();

        long memberId = member.getId();
        long totalPositions = positionRepository.countByMemberId(memberId);

        // if member has only one position, set it's new position to "MEMBER" then delete it.
        if (totalPositions == 1){

            addPositionToMember(memberId, createDefaultMemberPosition());
            positionRepository.deleteById(positionId);

            return ("position %d deleted and member's " +
                    "new position is set to \"MEMBER\"")
                    .formatted(positionId);
        }

        // if to be deleted position is active and it's not the only position member has,
        // set last position to active then delete it.
        else if (positionToDelete.isActive()){

            Position lastPosition = positionRepository
                    .findLastPositionByPositionId(positionId)
                    .orElseThrow(()-> new IllegalStateException(("could not find the previous position")));

            lastPosition.setActive(true);

            positionRepository.deleteById(positionId);
            return ("position %d deleted and member's " +
                    "previous position (id: %d) is active now.")
                    .formatted(positionId, lastPosition.getId());
        }

        // if the position is not the only position and not active one
        else{
            positionRepository.deleteById(positionId);
            return ("position %d deleted")
                    .formatted(positionId);
        }
    }
}
