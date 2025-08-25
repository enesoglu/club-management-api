package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.request.CreateTermRequest;
import com.cms.clubmanagementapi.dto.response.TermDTO;
import com.cms.clubmanagementapi.model.role.Term;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TermMapper {

    TermDTO toDTO(Term term);

    Term toEntity(CreateTermRequest createTermRequest);

}