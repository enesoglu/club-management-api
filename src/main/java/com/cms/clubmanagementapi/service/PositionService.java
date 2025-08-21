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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

import static com.cms.clubmanagementapi.model.role.Team.MEMBER;

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

    @Transactional
    public String deletePosition(@PathVariable long id){
        Position positionToBeDeleted = positionRepository.findById(id);

        // if to be deleted position is active and it's not the only position member has,
        // set last position to active and delete.
        if ((positionRepository.findAllPositionsByPositionId(id)).size() != 1){

            if (positionToBeDeleted.isActive()){
                Position lastPosition = positionRepository.findLastPositionByPositionId(id);
                lastPosition.setActive(true);

                positionRepository.deleteById(id);
                return ("position %d deleted and member's " +
                        "last position (id: %d) is active now.")
                        .formatted(id, lastPosition.getId());
            }
        }

        // if member has only one position, set position to "MEMBER".
        else{
            // get the member from position.
            ClubMember member = positionToBeDeleted.getMember();

            // create a member position.
            CreateClubMemberPosition positionRequest = new CreateClubMemberPosition();
            positionRequest.setTeam(MEMBER);

            // set member's position to "MEMBER".
            addPositionToMember(member.getId(), positionRequest);

            positionRepository.deleteById(id);
            return ("position %d deleted and member's " +
                    "new position is set to \"MEMBER\"")
                    .formatted(id);
        }

        // delete the position
        positionRepository.deleteById(id);
        return "";
    }
}
