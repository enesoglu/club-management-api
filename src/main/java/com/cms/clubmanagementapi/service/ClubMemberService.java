package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.dto.request.UpdateClubMemberRequest;
import com.cms.clubmanagementapi.dto.response.ClubMemberDTO;
import com.cms.clubmanagementapi.dto.request.CreateClubMemberRequest;
import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.YearOfStudy;
import com.cms.clubmanagementapi.model.role.Position;
import com.cms.clubmanagementapi.model.role.Term;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;
import com.cms.clubmanagementapi.mapper.ClubMemberMapper;
import com.cms.clubmanagementapi.repository.TermRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public ClubMemberService(ClubMemberRepository clubMemberRepository,
                             ClubMemberMapper clubMemberMapper,
                             TermRepository termRepository,
                             PositionService positionService,
                             PasswordEncoder passwordEncoder) {
        this.clubMemberRepository = clubMemberRepository;
        this.clubMemberMapper = clubMemberMapper;
        this.termRepository = termRepository;
        this.positionService = positionService;
        this.passwordEncoder = passwordEncoder;
    }

    // get all members
    public List<ClubMemberDTO> findAllMembers() {
        List<ClubMember> members = clubMemberRepository.findAllWithPositionsAndTerms();
        return clubMemberMapper.toDTOList(members);
    }

    // find member by id
    public ClubMemberDTO findMemberById(long id) {
        ClubMember member = clubMemberRepository.findByIdWithPositionsAndTerms(id)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));
        return clubMemberMapper.toDTO(member);
    }

    // find member by email
    public ClubMemberDTO findMemberByEmail(String email) {
        ClubMember member = clubMemberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));
        return clubMemberMapper.toDTO(member);
    }

    // create new member
    @Transactional
    public ClubMemberDTO createMember(CreateClubMemberRequest memberRequest) {

        // make member's name Title Case
        memberRequest.setName(toTitleCase(memberRequest.getName()));

        // encode the password
        encodePasswordForMember(memberRequest);

        // create a member entity from coming DTO
        ClubMember newMember = clubMemberMapper.toEntity(memberRequest);

        // find and get the active term
        Term activeTerm = termRepository.findByIsActiveTrue()
                .orElseThrow(() -> new RuntimeException("No Active Term"));

        Position newPosition = positionService.createPositionForNewMember(
                newMember,
                memberRequest.getPosition(),
                activeTerm
        );

        // add the position to the member's position list
        newMember.setPositions(List.of(newPosition));

        //  save the member
        ClubMember savedMember = clubMemberRepository.save(newMember);

        return clubMemberMapper.toDTO(savedMember);

    }

    // delete a member
    public void deleteMember(@PathVariable Long id) {
        clubMemberRepository.deleteById(id);
    }

    // update member
    public ClubMemberDTO updateMember(long id, UpdateClubMemberRequest memberRequest) {
        ClubMember existingMember = clubMemberRepository.findById(id)
                                         .orElseThrow(() -> new RuntimeException("Member Not Found"));

        existingMember.setName(toTitleCase(memberRequest.getName()));
        existingMember.setEmail(memberRequest.getEmail());
        existingMember.setPhone(memberRequest.getPhone());
        existingMember.setSchoolNo(memberRequest.getSchoolNo());
        existingMember.setYearOfStudy(memberRequest.getYearOfStudy());
        existingMember.setFaculty(memberRequest.getFaculty());
        existingMember.setDepartment(memberRequest.getDepartment());
        existingMember.setMembershipStatus(memberRequest.getMembershipStatus());

        ClubMember updatedMember = clubMemberRepository.save(existingMember);

        return clubMemberMapper.toDTO(updatedMember);
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

                encodePasswordForMember(newMemberRequest);
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

    private void encodePasswordForMember(CreateClubMemberRequest member) {
        String rawPassword = member.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encodedPassword);

    }
}