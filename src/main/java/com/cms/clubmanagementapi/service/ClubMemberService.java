package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.MemberRole;
import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;

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
import java.util.List;

@Service
public class ClubMemberService {

    private final ClubMemberRepository clubMemberRepository;

    public ClubMemberService(ClubMemberRepository clubMemberRepository) {
        this.clubMemberRepository = clubMemberRepository;
    }

    // get all members
    public List<ClubMember> findAllMembers(){
        return clubMemberRepository.findAll();
    }

    // create new member
    public ClubMember createMember(ClubMember member){
        return clubMemberRepository.save(member);
    }

    // delete a member
    public void deleteMember(@PathVariable Long id) { clubMemberRepository.deleteById(id);}

    // create new members from csv file
    @Transactional
    public void saveMembersFromCsv(MultipartFile file) throws IOException {

        final CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        List<ClubMember> membersToSave = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = CSVParser.parse(reader, format)) {

            for (CSVRecord csvRecord : csvParser) {
                ClubMember newMember = new ClubMember();

                newMember.setName(csvRecord.get("name"));
                newMember.setEmail(csvRecord.get("email"));
                newMember.setPhone(csvRecord.get("phone"));
                newMember.setSchoolNo(csvRecord.get("schoolNo"));
                newMember.setEmail(csvRecord.get("email"));
                newMember.setYearOfStudy(csvRecord.get("yearOfStudy"));
                newMember.setFaculty(csvRecord.get("faculty"));
                newMember.setDepartment(csvRecord.get("department"));
                //TODO
                // newMember.setRole(MemberRole.MEMBER);
                newMember.setMembershipStatus(MemberStatus.ACTIVE);

                membersToSave.add(newMember);
            }
        }

        if (!membersToSave.isEmpty()) {
            clubMemberRepository.saveAll(membersToSave);
        }
    }
}
