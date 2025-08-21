package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.dto.response.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.request.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.role.Position;
import com.cms.clubmanagementapi.model.role.Team;
import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;
import com.cms.clubmanagementapi.mapper.ClubMemberMapper;
import com.cms.clubmanagementapi.repository.TermRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberMapper clubMemberMapper;
    private final TermRepository termRepository;
    private final PositionService positionService;

    public ClubMemberService(ClubMemberRepository clubMemberRepository,
                             ClubMemberMapper clubMemberMapper,
                             TermRepository termRepository, PositionService positionService) {
        this.clubMemberRepository = clubMemberRepository;
        this.clubMemberMapper = clubMemberMapper;
        this.termRepository = termRepository;
        this.positionService = positionService;
    }

    // get all members
    public List<ClubMemberDTO> findAllMembers() {
        List<ClubMember> members = clubMemberRepository.findAllWithPositionsAndTerms();
        return clubMemberMapper.toDTOList(members);
    }

    public ClubMemberDTO findMemberById(long id) {
        ClubMember member = clubMemberRepository.findByIdWithPositionsAndTerms(id)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));
        return clubMemberMapper.toDTO(member);
    }

    // create new member
    @Transactional
    public ClubMemberDTO createMember(CreateClubMemberRequest member) {

        // create a member entity from coming DTO
        ClubMember newMember = clubMemberMapper.toEntity(member);

        // find and get the active term
        Term activeTerm = termRepository.findByIsActiveTrue()
                .orElseThrow(() -> new RuntimeException("No Active Term"));

        // set member to the default position -> "MEMBER"
        Position defaultPosition = new Position();
        defaultPosition.setMember(newMember);               // assign position to it's owner member
        defaultPosition.setTerm(activeTerm);                // position's term
        defaultPosition.setTeam(Team.MEMBER);               // default position member
        defaultPosition.setStartDate(LocalDate.now());      // position's start date
        defaultPosition.setActive(true);                    // position is active


        // add the position to the member's position list
        newMember.setPositions(List.of(defaultPosition));

        //  save the member
        ClubMember savedMember = clubMemberRepository.save(newMember);

        return clubMemberMapper.toDTO(savedMember);

    }

    // delete a member
    public void deleteMember(@PathVariable Long id) {
        clubMemberRepository.deleteById(id);
    }

    // create new members from csv file
    @Transactional
    public void saveMembersFromCsv(MultipartFile file) throws IOException {

        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        List<CreateClubMemberRequest> membersToSave = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, format)) {

            for (CSVRecord csvRecord : csvParser) {
                CreateClubMemberRequest newMember = new CreateClubMemberRequest();

                newMember.setName(csvRecord.get("name"));
                newMember.setEmail(csvRecord.get("email"));
                newMember.setPhone(csvRecord.get("phone"));
                newMember.setSchoolNo(csvRecord.get("schoolNo"));
                newMember.setNationalId(csvRecord.get("nationalId"));
                newMember.setYearOfStudy(csvRecord.get("yearOfStudy"));
                newMember.setFaculty(csvRecord.get("faculty"));
                newMember.setDepartment(csvRecord.get("department"));
                newMember.setPassword(csvRecord.get("password"));
                // all new members are active by default
                newMember.setMembershipStatus(MemberStatus.ACTIVE);
                newMember.setPosition(positionService.createDefaultMemberPositionRequest());

                membersToSave.add(newMember);
            }
        }

        if (!membersToSave.isEmpty()) {
            List<ClubMember> clubMembers = clubMemberMapper.toEntityList(membersToSave);
            clubMemberRepository.saveAll(clubMembers);
        }
    }
}