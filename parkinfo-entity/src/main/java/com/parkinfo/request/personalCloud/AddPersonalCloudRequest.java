package com.parkinfo.request.personalCloud;

import com.parkinfo.response.personalCloud.UploadFileResponse;
import lombok.Data;
import java.util.List;

@Data
public class AddPersonalCloudRequest {
    private List<UploadFileResponse> uploadFileResponses;
}
