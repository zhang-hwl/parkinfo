package com.parkinfo.initPermission;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.parkinfo.enums.ParkRoleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li
 * description:
 * date: 2019-10-21
 */
@Component
public class InitPermissionConfiguration {

    @Bean
    public Map<String, List<String>> initPermission(){
        String str = "{\"HR_USER\":[\"1d5f88e348114070b108e01b14d63833\",\"402808816d1f90c6016d1f9e90090000\",\"402808816d1f90c6016d1f9e90090010\",\"5095ac89c30c462aa2ce19458447a124\",\"5095ac89c30c462aa2ce19458447a212\",\"5095ac89c30c462aa2ce19458447a2ad\",\"5095ac89c30c462aa2ce19458447a2ae\",\"5095ac89c30c462aa2ce19458447a2ba\",\"5095ac89c30c462aa2ce19458447a2bb\",\"5095ac89c30c462aa2ce19458447a2bc\",\"5095ac89c30c462aa2ce19458447a3a1\",\"5095ac89c30c462aa2ce19458447a3a2\",\"5095ac89c30c462aa2ce19458447a3a5\",\"5095ac89c30c462aa2ce19458447a3c2\",\"5095ac89c30c462aa2ce19458447a3c3\",\"5095ac89c30c462aa2ce19458447a3c5\",\"5095ac89c30c462aa2ce19458447a3c9\",\"5095ac89c30c462aa2ce19458447a3d6\",\"5095ac89c30c462aa2ce19458447a3e5\",\"5095ac89c30c462aa2ce19458447a3e8\",\"5095ac89c30c462aa2ce19458447aaaa\",\"5095ac89c30c462aa2ce19458447aaja\",\"5095ac89c30c462aa2ce19458447aala\",\"5095ac89c30c462aa2ce19458447adaa\",\"5095ac89c30c462aa2ce19458447adba\",\"5095ac89c30c462aa2ce19458447adga\",\"760dd7f525fb4cc7816d084171e80e85\",\"8961db6725034515b827d599f3f27508\",\"8961db6725034515b827d599f3f27512\",\"d0779df9a2f7413ebd39a946d2924c11\",\"d0779df9a2f7413ebd39a946d2924c49\",\"ebaaa89082784aa2a1f30df6c7qw87a9\",\"ebaaa89082784aa2b1f30df6b78f87a5\",\"ebaaa89082784aa2b1f30df6b78f87a7\",\"ebaaa89082787aa2a1f30df6c7qw8zzd\",\"0d18797f9ab4463d9f16b8ba22f1b5dd\",\"402808816d1f90c6016d1f9e90090013\",\"402808816d1f90c6016d1f9e90090014\",\"5095ac89c30c462aa2ce19458447a123\",\"5095ac89c30c462aa2ce19458447a2a1\",\"5095ac89c30c462aa2ce19458447a2a2\",\"5095ac89c30c462aa2ce19458447a2a3\",\"5095ac89c30c462aa2ce19458447a2a4\",\"5095ac89c30c462aa2ce19458447a2b1\",\"5095ac89c30c462aa2ce19458447a2b2\",\"5095ac89c30c462aa2ce19458447a2b6\",\"5095ac89c30c462aa2ce19458447a3a0\",\"5095ac89c30c462aa2ce19458447a3c6\",\"5095ac89c30c462aa2ce19458447a3c8\",\"5095ac89c30c462aa2ce19458447a3d7\",\"5095ac89c30c462aa2ce19458447a3e1\",\"5095ac89c30c462aa2ce19458447a3e3\",\"5095ac89c30c462aa2ce19458447a3e4\",\"5095ac89c30c462aa2ce19458447a3f2\",\"5095ac89c30c462aa2ce19458447a3f5\",\"5095ac89c30c462aa2ce19458447a3z2\",\"5095ac89c30c462aa2ce19458447a3z3\",\"5095ac89c30c462aa2ce19458447a3z4\",\"5095ac89c30c462aa2ce19458447a3z5\",\"5095ac89c30c462aa2ce19458447aaj1\",\"5095ac89c30c462aa2ce19458447aal1\",\"5095ac89c30c462aa2ce19458447acaa\",\"5095ac89c30c462aa2ce19458447adbb\",\"5095ac89c30c462aa2ce19458447adgb\",\"5095ac89c30c462aa2ce19458447zzz9\",\"5ca79922f393424ebdbc7559bb35e630\",\"5ca79922f393424ebdbc7559bb35e631\",\"5ca79922f393424ebdbc7559bb35e632\",\"5ca79922f393424ebdbc7559bb35e633\",\"5ca79922f393424ebdbc7559bb35e634\",\"6a97818515ea4d309f7e950af67a1100\",\"6a97818515ea4d309f7e950af67a1101\",\"91ee81a13aef45bc97f133aa8309afe6\",\"9550d4d6051f45c099dd686a1ddbe902\",\"d0779df9a2f7413ebd39a946d2923333\",\"ebaaa89082784aa2b1f30df6b78f87a9\",\"ebaaa89082787aa2a1f30df6c7qw8z1c\",\"ebaaa89082787aa2a1f30df6c7qwzzzc\"],\n" +
                "\"OFFICER\":[\"32058aae9bad4df09f4137ad8bc5c412\",\"402808816d1f90c6016d1f9e90090000\",\"402808816d1f90c6016d1f9e90090010\",\"402808816d1f90c6016d1f9e90090030\",\"5095ac89c30c462aa2ce19458447a212\",\"5095ac89c30c462aa2ce19458447a2ad\",\"5095ac89c30c462aa2ce19458447a2ae\",\"5095ac89c30c462aa2ce19458447a2ba\",\"5095ac89c30c462aa2ce19458447a2bb\",\"5095ac89c30c462aa2ce19458447a2bc\",\"5095ac89c30c462aa2ce19458447a3a1\",\"5095ac89c30c462aa2ce19458447a3a2\",\"5095ac89c30c462aa2ce19458447a3a5\",\"5095ac89c30c462aa2ce19458447a3c2\",\"5095ac89c30c462aa2ce19458447a3c3\",\"5095ac89c30c462aa2ce19458447a3c5\",\"5095ac89c30c462aa2ce19458447a3c9\",\"5095ac89c30c462aa2ce19458447a3d1\",\"5095ac89c30c462aa2ce19458447a3d6\",\"5095ac89c30c462aa2ce19458447a3e5\",\"5095ac89c30c462aa2ce19458447a3e8\",\"5095ac89c30c462aa2ce19458447aaaa\",\"5095ac89c30c462aa2ce19458447aaba\",\"5095ac89c30c462aa2ce19458447aaca\",\"5095ac89c30c462aa2ce19458447aada\",\"5095ac89c30c462aa2ce19458447aaea\",\"5095ac89c30c462aa2ce19458447aafa\",\"5095ac89c30c462aa2ce19458447aaga\",\"5095ac89c30c462aa2ce19458447aaha\",\"5095ac89c30c462aa2ce19458447aaia\",\"5095ac89c30c462aa2ce19458447aaja\",\"5095ac89c30c462aa2ce19458447aaka\",\"5095ac89c30c462aa2ce19458447aala\",\"5095ac89c30c462aa2ce19458447adaa\",\"5095ac89c30c462aa2ce19458447adba\",\"5095ac89c30c462aa2ce19458447adca\",\"5095ac89c30c462aa2ce19458447adda\",\"5095ac89c30c462aa2ce19458447adea\",\"5095ac89c30c462aa2ce19458447adfa\",\"5095ac89c30c462aa2ce19458447adga\",\"760dd7f525fb4cc7816d084171e80e85\",\"8961db6725034515b827d599f3f27508\",\"8961db6725034515b827d599f3f27512\",\"a4e009789b8c4c2abe56e99bf841d2ca\",\"d0779df9a2f7413ebd39a946d2924c11\",\"d0779df9a2f7413ebd39a946d2924c49\",\"ebaaa89082784aa2a1f30df6c78f87a9\",\"ebaaa89082784aa2b1f30df6b78f87a5\",\"ebaaa89082784aa2b1f30df6b78f87a7\",\"ebaaa89082787aa2a1f30df6c7qw8zzd\",\"0d18797f9ab4463d9f16b8ba22f1b5dd\",\"33a0732fd0f845a09bc019fbb556c8e0\",\"33a0732fd0f845a09bc019fbb556c8e1\",\"402808816d1f90c6016d1f9e90090013\",\"402808816d1f90c6016d1f9e90090014\",\"402808816d1f90c6016d1f9e90090033\",\"402808816d1f90c6016d1f9e90090039\",\"5095ac89c30c462aa2ce19458447a123\",\"5095ac89c30c462aa2ce19458447a2a1\",\"5095ac89c30c462aa2ce19458447a2a2\",\"5095ac89c30c462aa2ce19458447a2a3\",\"5095ac89c30c462aa2ce19458447a2a4\",\"5095ac89c30c462aa2ce19458447a2b1\",\"5095ac89c30c462aa2ce19458447a2b2\",\"5095ac89c30c462aa2ce19458447a2b6\",\"5095ac89c30c462aa2ce19458447a3a0\",\"5095ac89c30c462aa2ce19458447a3a3\",\"5095ac89c30c462aa2ce19458447a3a6\",\"5095ac89c30c462aa2ce19458447a3d2\",\"5095ac89c30c462aa2ce19458447a3d7\",\"5095ac89c30c462aa2ce19458447a3e1\",\"5095ac89c30c462aa2ce19458447a3f3\",\"5095ac89c30c462aa2ce19458447a3z2\",\"5095ac89c30c462aa2ce19458447a3z3\",\"5095ac89c30c462aa2ce19458447a3z4\",\"5095ac89c30c462aa2ce19458447a3z7\",\"5095ac89c30c462aa2ce19458447aaa3\",\"5095ac89c30c462aa2ce19458447aabb\",\"5095ac89c30c462aa2ce19458447aacb\",\"5095ac89c30c462aa2ce19458447aadb\",\"5095ac89c30c462aa2ce19458447aaeb\",\"5095ac89c30c462aa2ce19458447aafb\",\"5095ac89c30c462aa2ce19458447aagb\",\"5095ac89c30c462aa2ce19458447aahb\",\"5095ac89c30c462aa2ce19458447aaib\",\"5095ac89c30c462aa2ce19458447aaj1\",\"5095ac89c30c462aa2ce19458447aak1\",\"5095ac89c30c462aa2ce19458447aal1\",\"5095ac89c30c462aa2ce19458447acaa\",\"5095ac89c30c462aa2ce19458447adbb\",\"5095ac89c30c462aa2ce19458447adcb\",\"5095ac89c30c462aa2ce19458447addb\",\"5095ac89c30c462aa2ce19458447adeb\",\"5095ac89c30c462aa2ce19458447adfb\",\"5095ac89c30c462aa2ce19458447adgb\",\"5095ac89c30c462aa2ce19458447zzz8\",\"5095ac89c30c462aa2ce19458447zzz9\",\"5ca79922f393424ebdbc7559bb35e630\",\"5ca79922f393424ebdbc7559bb35e631\",\"5ca79922f393424ebdbc7559bb35e632\",\"5ca79922f393424ebdbc7559bb35e633\",\"5ca79922f393424ebdbc7559bb35e634\",\"91ee81a13aef45bc97f133aa8309afe6\",\"9550d4d6051f45c099dd686a1ddbe902\",\"d0779df9a2f7413ebd39a946d2923333\",\"d23482c49c954b3b846c37f75c16d490\",\"d23482c49c954b3b846c37f75c16d491\",\"ebaaa89082784aa2b1f30df6b78f87a9\",\"ebaaa89082787aa2a1f30df6c7qw8z1c\",\"ebaaa89082787aa2a1f30df6c7qwzzzb\"],\n" +
                "\"PARK_USER\":[\"1d5f88e348114070b108e01b14d63833\",\"32058aae9bad4df09f4137ad8bc5c412\",\"402808816d1f90c6016d1f9e90090000\",\"402808816d1f90c6016d1f9e90090010\",\"402808816d1f90c6016d1f9e90090020\",\"402808816d1f90c6016d1f9e90090030\",\"402808816d1f90c6016d1f9e9009aaaa\",\"5095ac89c30c462aa2ce19458447a212\",\"5095ac89c30c462aa2ce19458447a2ad\",\"5095ac89c30c462aa2ce19458447a2ae\",\"5095ac89c30c462aa2ce19458447a2ba\",\"5095ac89c30c462aa2ce19458447a2bb\",\"5095ac89c30c462aa2ce19458447a2bc\",\"5095ac89c30c462aa2ce19458447a2bd\",\"5095ac89c30c462aa2ce19458447a3a1\",\"5095ac89c30c462aa2ce19458447a3a2\",\"5095ac89c30c462aa2ce19458447a3a5\",\"5095ac89c30c462aa2ce19458447a3c2\",\"5095ac89c30c462aa2ce19458447a3c5\",\"5095ac89c30c462aa2ce19458447a3c9\",\"5095ac89c30c462aa2ce19458447a3d1\",\"5095ac89c30c462aa2ce19458447a3d6\",\"5095ac89c30c462aa2ce19458447a3e5\",\"5095ac89c30c462aa2ce19458447a3e6\",\"5095ac89c30c462aa2ce19458447a3e8\",\"5095ac89c30c462aa2ce19458447aaaa\",\"5095ac89c30c462aa2ce19458447aaba\",\"5095ac89c30c462aa2ce19458447aaca\",\"5095ac89c30c462aa2ce19458447aada\",\"5095ac89c30c462aa2ce19458447aaea\",\"5095ac89c30c462aa2ce19458447aafa\",\"5095ac89c30c462aa2ce19458447aaga\",\"5095ac89c30c462aa2ce19458447aaha\",\"5095ac89c30c462aa2ce19458447aaia\",\"5095ac89c30c462aa2ce19458447aaja\",\"5095ac89c30c462aa2ce19458447aaka\",\"5095ac89c30c462aa2ce19458447aala\",\"5095ac89c30c462aa2ce19458447adaa\",\"5095ac89c30c462aa2ce19458447adba\",\"5095ac89c30c462aa2ce19458447adca\",\"5095ac89c30c462aa2ce19458447adda\",\"5095ac89c30c462aa2ce19458447adea\",\"5095ac89c30c462aa2ce19458447adfa\",\"5095ac89c30c462aa2ce19458447adga\",\"5095ac89c30c462aa2ce19458447adha\",\"760dd7f525fb4cc7816d084171e80e85\",\"8961db6725034515b827d599f3f27508\",\"8961db6725034515b827d599f3f27511\",\"8961db6725034515b827d599f3f27512\",\"8961db6725034515b827d599f3f27513\",\"b05a99efeeec4badb65c3c45bce32612\",\"b05a99efeeec4badb65c3c45bce32634\",\"c66bb6f2beef41d4bc9673314eedec15\",\"d0779df9a2f7413ebd39a946d2924c11\",\"d0779df9a2f7413ebd39a946d2924c12\",\"d0779df9a2f7413ebd39a946d2924c49\",\"ebaaa89082784aa2b1f30df6b78f87a5\",\"ebaaa89082784aa2b1f30df6b78f87a7\",\"ebaaa89082787aa2a1f30df6c7qw87a9\",\"ebaaa89082787aa2a1f30df6c7qw8zzb\",\"ebaaa89082787aa2a1f30df6c7qw8zzc\",\"ebaaa89082787aa2a1f30df6c7qw8zzd\",\"ebaaa89082787aa2a1f30df6c7qw8zze\",\"0d18797f9ab4463d9f16b8ba22f1b5dd\",\"12312312sasdasdasd12312sdadasdas\",\"141589c5ba13403ab2d02fcaceebac35\",\"252242c4cc03493b8e0ccb50e2f9b64c\",\"33a0732fd0f845a09bc019fbb556c8e0\",\"33a0732fd0f845a09bc019fbb556c8e1\",\"402808816d1f90c6016d1f9e90090011\",\"402808816d1f90c6016d1f9e90090012\",\"402808816d1f90c6016d1f9e90090013\",\"402808816d1f90c6016d1f9e90090014\",\"402808816d1f90c6016d1f9e90090015\",\"402808816d1f90c6016d1f9e90090021\",\"402808816d1f90c6016d1f9e90090023\",\"402808816d1f90c6016d1f9e90090024\",\"402808816d1f90c6016d1f9e90090025\",\"402808816d1f90c6016d1f9e90090027\",\"402808816d1f90c6016d1f9e90090028\",\"402808816d1f90c6016d1f9e90090029\",\"402808816d1f90c6016d1f9e90090033\",\"402808816d1f90c6016d1f9e90090035\",\"402808816d1f90c6016d1f9e90090039\",\"402808816d1f90c6016d1f9e9009aaa1\",\"5095ac89c30c462aa2ce19458447a123\",\"5095ac89c30c462aa2ce19458447a2a1\",\"5095ac89c30c462aa2ce19458447a2a2\",\"5095ac89c30c462aa2ce19458447a2a3\",\"5095ac89c30c462aa2ce19458447a2a4\",\"5095ac89c30c462aa2ce19458447a2b1\",\"5095ac89c30c462aa2ce19458447a2b2\",\"5095ac89c30c462aa2ce19458447a2b3\",\"5095ac89c30c462aa2ce19458447a2b4\",\"5095ac89c30c462aa2ce19458447a2b5\",\"5095ac89c30c462aa2ce19458447a2b6\",\"5095ac89c30c462aa2ce19458447a2za\",\"5095ac89c30c462aa2ce19458447a3a0\",\"5095ac89c30c462aa2ce19458447a3a3\",\"5095ac89c30c462aa2ce19458447a3a4\",\"5095ac89c30c462aa2ce19458447a3a6\",\"5095ac89c30c462aa2ce19458447a3a7\",\"5095ac89c30c462aa2ce19458447a3a8\",\"5095ac89c30c462aa2ce19458447a3c1\",\"5095ac89c30c462aa2ce19458447a3d2\",\"5095ac89c30c462aa2ce19458447a3d3\",\"5095ac89c30c462aa2ce19458447a3d4\",\"5095ac89c30c462aa2ce19458447a3d5\",\"5095ac89c30c462aa2ce19458447a3d7\",\"5095ac89c30c462aa2ce19458447a3d8\",\"5095ac89c30c462aa2ce19458447a3d9\",\"5095ac89c30c462aa2ce19458447a3e1\",\"5095ac89c30c462aa2ce19458447a3e3\",\"5095ac89c30c462aa2ce19458447a3e4\",\"5095ac89c30c462aa2ce19458447a3e7\",\"5095ac89c30c462aa2ce19458447a3e9\",\"5095ac89c30c462aa2ce19458447a3f1\",\"5095ac89c30c462aa2ce19458447a3f2\",\"5095ac89c30c462aa2ce19458447a3f3\",\"5095ac89c30c462aa2ce19458447a3f4\",\"5095ac89c30c462aa2ce19458447a3f5\",\"5095ac89c30c462aa2ce19458447a3f6\",\"5095ac89c30c462aa2ce19458447a3z2\",\"5095ac89c30c462aa2ce19458447a3z3\",\"5095ac89c30c462aa2ce19458447a3z7\",\"5095ac89c30c462aa2ce19458447aaa1\",\"5095ac89c30c462aa2ce19458447aaa2\",\"5095ac89c30c462aa2ce19458447aaa3\",\"5095ac89c30c462aa2ce19458447aabb\",\"5095ac89c30c462aa2ce19458447aabc\",\"5095ac89c30c462aa2ce19458447aacb\",\"5095ac89c30c462aa2ce19458447aacc\",\"5095ac89c30c462aa2ce19458447aadb\",\"5095ac89c30c462aa2ce19458447aadc\",\"5095ac89c30c462aa2ce19458447aaeb\",\"5095ac89c30c462aa2ce19458447aaec\",\"5095ac89c30c462aa2ce19458447aafb\",\"5095ac89c30c462aa2ce19458447aafc\",\"5095ac89c30c462aa2ce19458447aagb\",\"5095ac89c30c462aa2ce19458447aagc\",\"5095ac89c30c462aa2ce19458447aahb\",\"5095ac89c30c462aa2ce19458447aahc\",\"5095ac89c30c462aa2ce19458447aaib\",\"5095ac89c30c462aa2ce19458447aaic\",\"5095ac89c30c462aa2ce19458447aaj1\",\"5095ac89c30c462aa2ce19458447aaj2\",\"5095ac89c30c462aa2ce19458447aak1\",\"5095ac89c30c462aa2ce19458447aak2\",\"5095ac89c30c462aa2ce19458447aal1\",\"5095ac89c30c462aa2ce19458447aal2\",\"5095ac89c30c462aa2ce19458447acaa\",\"5095ac89c30c462aa2ce19458447adbb\",\"5095ac89c30c462aa2ce19458447adbc\",\"5095ac89c30c462aa2ce19458447adbd\",\"5095ac89c30c462aa2ce19458447adcb\",\"5095ac89c30c462aa2ce19458447adcc\",\"5095ac89c30c462aa2ce19458447adcd\",\"5095ac89c30c462aa2ce19458447addb\",\"5095ac89c30c462aa2ce19458447addc\",\"5095ac89c30c462aa2ce19458447addd\",\"5095ac89c30c462aa2ce19458447adeb\",\"5095ac89c30c462aa2ce19458447adec\",\"5095ac89c30c462aa2ce19458447aded\",\"5095ac89c30c462aa2ce19458447adfb\",\"5095ac89c30c462aa2ce19458447adfc\",\"5095ac89c30c462aa2ce19458447adfd\",\"5095ac89c30c462aa2ce19458447adgb\",\"5095ac89c30c462aa2ce19458447adgc\",\"5095ac89c30c462aa2ce19458447adgd\",\"5095ac89c30c462aa2ce19458447adh1\",\"5095ac89c30c462aa2ce19458447adh2\",\"5095ac89c30c462aa2ce19458447adh3\",\"5095ac89c30c462aa2ce19458447zzz8\",\"5095ac89c30c462aa2ce19458447zzz9\",\"5095ac89c30c462aa2ce19458aa7aaaa\",\"5ca79922f393424ebdbc7559bb35e630\",\"5ca79922f393424ebdbc7559bb35e631\",\"5ca79922f393424ebdbc7559bb35e632\",\"5ca79922f393424ebdbc7559bb35e633\",\"5ca79922f393424ebdbc7559bb35e634\",\"5ca79922f393424ebdbc7559bb35e635\",\"5ca79922f393424ebdbc7559bb35e636\",\"5ca79922f393424ebdbc7559bb35e637\",\"5ca79922f393424ebdbc7559bb35e63a\",\"5ca79922f393424ebdbc7559bb35e63b\",\"5ca79922f393424ebdbc7559bb35e63c\",\"6a97818515ea4d309f7e950af67a1100\",\"6a97818515ea4d309f7e950af67a1101\",\"91ee81a13aef45bc97f133aa8309afe6\",\"9550d4d6051f45c099dd686a1ddbe902\",\"970ff121884d41cda654c41f034e5853\",\"97391f3cd499406f8ceb06331bd1846f\",\"a06639f863e6477b92cccde062200a10\",\"a06639f863e6477b92cccde062200a11\",\"a06639f863e6477b92cccde062200a12\",\"a06639f863e6477b92cccde062200a13\",\"a06639f863e6477b92cccde062200a14\",\"af9b74df3955416f8625b1c44cd82f87\",\"b05a99efeeec4badb65c3c45bce32621\",\"b05a99efeeec4badb65c3c45bce32622\",\"b05a99efeeec4badb65c3c45bce32623\",\"b05a99efeeec4badb65c3c45bce32628\",\"b05a99efeeec4badb65c3c45bce32629\",\"d0779df9a2f7413ebd39a946d2923333\",\"ebaaa89082784aa2b1f30df6b78f87a6\",\"ebaaa89082784aa2b1f30df6b78f87a9\",\"ebaaa89082787aa2a1f30df6c7qw8z1b\",\"ebaaa89082787aa2a1f30df6c7qw8z1c\",\"ebaaa89082787aa2a1f30df6c7qw8z1d\",\"ebaaa89082787aa2a1f30df6c7qw8z1e\",\"ebaaa89082787aa2a1f30df6c7qw8zza\",\"ebaaa89082787aa2a1f30df6c7qwzzzd\"]}\n";
        JSONObject jsonObject = JSON.parseObject(str);
        Map<String, List<String>> map = new HashMap<>();
        map.put(ParkRoleEnum.HR_USER.name(), jsonObject.getJSONArray(ParkRoleEnum.HR_USER.name()).toJavaList(String.class));
        map.put(ParkRoleEnum.OFFICER.name(), jsonObject.getJSONArray(ParkRoleEnum.OFFICER.name()).toJavaList(String.class));
        map.put(ParkRoleEnum.PARK_USER.name(), jsonObject.getJSONArray(ParkRoleEnum.PARK_USER.name()).toJavaList(String.class));
        return map;
    }

}