package org.example.bankproject.transfer;

import lombok.RequiredArgsConstructor;
import org.example.bankproject.transfer.jpa.TransferRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

}
