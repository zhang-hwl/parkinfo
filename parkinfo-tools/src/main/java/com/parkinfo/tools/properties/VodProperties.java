package com.parkinfo.tools.properties;

import lombok.Data;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-07-24 10:17
 **/
@Data
public class VodProperties {

    private String regionId = "cn-shanghai";  // 点播服务接入区域

    private String accessKeyId;

    private String accessKeySecret;

    private String transcodeTemplateGroupId;
}
