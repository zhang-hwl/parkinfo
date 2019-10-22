package com.parkinfo.request.sysConfig;

import lombok.Data;

import java.util.List;

@Data
public class InitUserPermission {

    private String roleName;

    private List<String> permissionIds;

}
