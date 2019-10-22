package com.parkinfo.request.sysConfig;

import lombok.Data;

import java.util.List;

/**
 * @author Li
 * description:
 * date: 2019-10-21
 */
@Data
public class InitPermissions {

    private List<InitUserPermission> list;

}
