package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.model.role.Position;
import com.cms.clubmanagementapi.model.role.Team;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;
import com.cms.clubmanagementapi.repository.PositionRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;
    private final PositionRepository positionRepository;

    public JpaUserDetailsService(ClubMemberRepository clubMemberRepository,
                                 PositionRepository positionRepository) {
        this.clubMemberRepository = clubMemberRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        ClubMember member = clubMemberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        // get member's active position
        Position activePosition = positionRepository.findActiveByMemberIdWithTerm(member.getId());

        if (activePosition != null) {

            // add position as authority (e.g: ROLE_MEMBER or ROLE_PRESIDENT)
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + activePosition.getTeam().name()));

            // if team is EXECUTIVE then also add member's title
            if (activePosition.getTeam() == Team.EXECUTIVE && activePosition.getExecutiveTitle() != null)
                grantedAuthorities.add(new SimpleGrantedAuthority(activePosition.getExecutiveTitle().name()));

            // if team is CREW then also add member's committee
            if (activePosition.getTeam() == Team.CREW && activePosition.getCrewCommittee() != null)
                    grantedAuthorities.add(new SimpleGrantedAuthority(activePosition.getCrewCommittee().name()));
        }

        return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
    }
}
