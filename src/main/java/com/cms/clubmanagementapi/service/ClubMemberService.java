package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.dto.response.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.request.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.YearOfStudy;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;
    private final ClubMemberMapper clubMemberMapper;
    private final TermRepository termRepository;
    private final PositionService positionService;

    public ClubMemberService(ClubMemberRepository clubMemberRepository,
                             ClubMemberMapper clubMemberMapper,
                             TermRepository termRepository,
                             PositionService positionService) {
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

        // make member's name Title Case
        member.setName(toTitleCase(member.getName()));

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
    public long saveMembersFromCsv(MultipartFile file) throws IOException {

        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        List<ClubMember> membersToSave = new ArrayList<>();
        Term activeTerm = termRepository.findByIsActiveTrue()
                .orElseThrow(() -> new RuntimeException("No Active Term"));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, format)) {

            for (CSVRecord csvRecord : csvParser) {
                CreateClubMemberRequest newMemberRequest = createMemberRequestFromCsv(csvRecord);
                ClubMember newMember =  clubMemberMapper.toEntity(newMemberRequest);

                Position defaultPosition = positionService.createDefaultPositionForMember(newMember, activeTerm);
                newMember.setPositions(List.of(defaultPosition));

                membersToSave.add(newMember);
            }
        }
        if (!membersToSave.isEmpty()) {
            clubMemberRepository.saveAll(membersToSave);
        }
        return membersToSave.size();
    }

    // --- helper methods ---

    private CreateClubMemberRequest createMemberRequestFromCsv(CSVRecord csvRecord) {
        CreateClubMemberRequest request = new CreateClubMemberRequest();
        request.setName(csvRecord.get("name"));
        request.setEmail(csvRecord.get("email"));
        request.setPhone(csvRecord.get("phone"));
        request.setSchoolNo(csvRecord.get("schoolNo"));
        request.setNationalId(csvRecord.get("nationalId"));
        request.setYearOfStudy(YearOfStudy.valueOf(csvRecord.get("yearOfStudy")));
        request.setFaculty(csvRecord.get("faculty"));
        request.setDepartment(csvRecord.get("department"));
        request.setPassword(csvRecord.get("password"));
        request.setMembershipStatus(MemberStatus.ACTIVE);
        return request;
    }

    private String toTitleCase(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return Arrays.stream(text.split("\\s+"))
                .map(word -> {
                    if (word.isEmpty()) {
                        return "";
                    }
                    return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
                })
                .collect(Collectors.joining(" "));
    }
}