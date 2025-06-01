package org.example.bankproject.exchange.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RateTable {

    private String tabel;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;
}
