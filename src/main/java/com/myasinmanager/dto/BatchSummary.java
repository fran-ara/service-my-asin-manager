package com.myasinmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchSummary {
    private Integer total = 0;
    private Integer success = 0;
    private Integer failed = 0;
    private String failedMessage;
    private List<String> itemsNotFound = new ArrayList<>();
    private List<FailedItems> failedItems = new ArrayList<>();

    @Data
    @Builder
    public static class FailedItems {
        private String asin;
        private String message;
    }

}
