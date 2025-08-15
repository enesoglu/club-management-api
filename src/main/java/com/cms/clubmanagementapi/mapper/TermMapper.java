package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.TermDTO;
import com.cms.clubmanagementapi.model.role.Term;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermMapper {
    TermDTO toDTO(Term term);
}