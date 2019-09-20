package com.parkinfo.request.sysConfig;

import lombok.Data;

import java.util.List;

@Data
public class SetPermissionRequest {

    private List<String> permissionIds;

    private String roleId;
}
