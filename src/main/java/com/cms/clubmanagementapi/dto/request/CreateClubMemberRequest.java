package com.cms.clubmanagementapi.dto.request;

import com.cms.clubmanagementapi.model.MemberStatus;
import com.cms.clubmanagementapi.model.YearOfStudy;
import jakarta.validation.constraints.*;
import lombok.Data;

//  create request dto
@Data
public class CreateClubMemberRequest {

    private static final String notBlankMessage = " cannot be blank.";

    @NotBlank(message = "Name" + notBlankMessage)
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "E-Mail" + notBlankMessage)
    @Email(message = "Please provide a valid e-mail address.")
    private String email;

    @NotBlank(message = "Phone Number" + notBlankMessage)
    @Pattern(regexp = "^05\\d{9}$", message = "Phone number must be in '05XXXXXXXXX' format.")
    private String phone;

    @NotBlank(message = "School No" + notBlankMessage)
    private String schoolNo;

    @NotBlank(message = "National Id" + notBlankMessage)
    @Size(min = 11, max = 11, message = "Turkish National ID must have 11 digit")
    @Pattern(regexp = "^[1-9]{1}[0-9]{9}[02468]{1}$", message = "Invalid Turkish National ID")
    private String nationalId;

    @NotBlank(message = "Study Year" + notBlankMessage)
    private YearOfStudy yearOfStudy;

    @NotBlank(message = "Faculty" + notBlankMessage)
    private String faculty;

    @NotBlank(message = "Department" + notBlankMessage)
    private String department;

    // TODO - for PASSWORD
    //   could be blank for the development stage
    //   @NotBlank(message = "Parola alanı boş bırakılamaz.")
    //   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
    //            message = "Password must contain at least one uppercase letter, one lowercase letter and one number ")
    //   @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    @NotNull(message = "Status" +  notBlankMessage)
    private MemberStatus membershipStatus;

    @NotNull(message = "Position" + notBlankMessage)
    private CreateMemberPositionRequest position;

}
