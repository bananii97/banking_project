package org.example.bankproject.bankBranchCodes;

import java.util.HashMap;
import java.util.Map;

public class BankBranchCode {

    public static Map<String, String> getVoivodeshipBranchCodes() {
        Map<String, String> map = new HashMap<>();
        map.put("Warszawa", "0010");
        map.put("Kraków", "0020");
        map.put("Łódź", "0030");
        map.put("Wrocław", "0040");
        map.put("Poznań", "0050");
        map.put("Gdańsk", "0060");
        map.put("Szczecin", "0070");
        map.put("Lublin", "0080");
        map.put("Białystok", "0090");
        map.put("Katowice", "0100");
        map.put("Olsztyn", "0110");
        map.put("Rzeszów", "0120");
        map.put("Kielce", "0130");
        map.put("Zielona Góra", "0140");
        map.put("Opole", "0150");
        map.put("Bydgoszcz", "0160");
        return map;
    }
}
