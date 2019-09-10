package com.parkinfo.tools.properties;

import lombok.Data;

@Data
public class OssProperties {
    private String endpoint="http://oss-cn-shanghai.aliyuncs.com";

    private String keyid;

    private String keysecret;

    private String bucketname;

    private String filehost;
}
