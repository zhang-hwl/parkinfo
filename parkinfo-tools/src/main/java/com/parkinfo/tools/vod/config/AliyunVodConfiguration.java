package com.parkinfo.tools.vod.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.parkinfo.tools.properties.ToolsProperties;
import com.parkinfo.tools.properties.VodProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-07-24 10:16
 **/
@Configuration
public class AliyunVodConfiguration {

    @Autowired
    private ToolsProperties toolsProperties;

    /**
     * 获得初始化的AliAcsClient
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAcsClient vodClient() throws ClientException {
        VodProperties vodProperties = toolsProperties.getVod();
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, vodProperties.getAccessKeyId(), vodProperties.getAccessKeySecret());
        return new DefaultAcsClient(profile);
    }
}
