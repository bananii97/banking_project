package org.example.bankproject.transfer.api;


import lombok.RequiredArgsConstructor;
import org.example.bankproject.transfer.TransferService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
}
