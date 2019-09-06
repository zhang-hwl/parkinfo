package com.parkinfo.web.temp;

import com.parkinfo.common.Result;
import com.parkinfo.entity.userConfig.ParkUser;
import com.parkinfo.exception.NormalException;
import com.parkinfo.repository.userConfig.ParkUserRepository;
import com.parkinfo.token.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-06 10:02
 **/
@RestController
@RequestMapping("/temp/")
@Api(value = "/temp/", tags = {"临时-测试用"})
public class TempController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ParkUserRepository parkUserRepository;

    @PostMapping("/generateToken")
    public Result<String> generateToken(){
        Optional<ParkUser> parkUserOptional = parkUserRepository.findById("fa8fb0515f7f4822a435fea9bb90dab5");
        if (!parkUserOptional.isPresent()){
            throw new NormalException("用户不存在");
        }
        ParkUser parkUser = parkUserOptional.get();
        String token = tokenUtils.generateTokeCode(parkUser,"2b441280acf24d87b5a1272c8f1162ee");
        return Result.<String>builder().success().data(token).build();
    }
}
