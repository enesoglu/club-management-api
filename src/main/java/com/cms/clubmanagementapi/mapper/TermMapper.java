package com.cms.clubmanagementapi.mapper;

import com.cms.clubmanagementapi.dto.request.CreateTermRequest;
import com.cms.clubmanagementapi.dto.response.TermDTO;
import com.cms.clubmanagementapi.model.role.Term;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TermMapper {

    TermDTO toDTO(Term term);

    List<TermDTO> toDTOList(List<Term> terms);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    Term toEntity(CreateTermRequest createTermRequest);

}