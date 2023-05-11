package com.example.concu.application.dto;

import com.example.concu.utils.GsonUtils;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@EqualsAndHashCode
public class CampaignCompletionNotificationInfo implements Serializable {
    private Long campaignId;
    private String title;
    private String name;

    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }

}
