package com.cms.clubmanagementapi.service;

import com.cms.clubmanagementapi.model.ClubMember;
import com.cms.clubmanagementapi.repository.ClubMemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    public JpaUserDetailsService(ClubMemberRepository clubMemberRepository) {
        this.clubMemberRepository = clubMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        ClubMember member = clubMemberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // TODO: "authorities" (third parameter) will be implemented
        return new User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }


}
