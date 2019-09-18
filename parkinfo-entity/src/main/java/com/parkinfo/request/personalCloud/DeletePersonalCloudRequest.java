package com.parkinfo.request.personalCloud;

import lombok.Data;

import java.util.List;

@Data
public class DeletePersonalCloudRequest {
    private List<String> ids;
}
