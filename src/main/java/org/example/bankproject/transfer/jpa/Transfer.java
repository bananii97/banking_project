package org.example.bankproject.transfer.jpa;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account ToAccount;

    private BigDecimal amount;
    private LocalDateTime dateTransfer;
}
