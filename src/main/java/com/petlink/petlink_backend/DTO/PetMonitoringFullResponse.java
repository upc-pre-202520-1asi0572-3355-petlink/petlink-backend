package com.petlink.petlink_backend.DTO;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetMonitoringFullResponse {
        private String name;
        private String owner;
        private String age;
        private String breed;
        private String admissionTime;
        private Integer currentHeartRate;
        private String status;
        private List<Integer> lastSixHeartRate;
}