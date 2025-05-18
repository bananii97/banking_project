package org.example.bankproject.identityCard;

import org.example.bankproject.identityCard.dto.IdentityCardDto;
import org.example.bankproject.identityCard.model.IdentityCard;

public class IdentityCardMapper {

    public static IdentityCardDto toDto(IdentityCard identityCard) {
        return IdentityCardDto.builder()
                .identityCardNumber(identityCard.getIdentityCardNumber())
                .issuePlace(identityCard.getIssuePlace())
                .issuingAuthority(identityCard.getIssuingAuthority())
                .validUntil(identityCard.getValidUntil())
                .build();
    }

    public static IdentityCard fromDto(IdentityCardDto personalDataDto) {
        return IdentityCard.builder()
                .identityCardNumber(personalDataDto.getIdentityCardNumber())
                .issuePlace(personalDataDto.getIssuePlace())
                .issuingAuthority(personalDataDto.getIssuingAuthority())
                .validUntil(personalDataDto.getValidUntil())
                .build();
    }
}
