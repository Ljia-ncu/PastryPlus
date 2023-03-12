package org.mrl.controller;

import org.mrl.component.JwtTokenManager;
import org.mrl.model.enums.TypeEnum;
import org.mrl.model.params.LikeRequestParam;
import org.mrl.mvc.RestResult;
import org.mrl.mvc.RestResultUtils;
import org.mrl.service.RecordService;
import org.mrl.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.Portal.PASTRY_PORTAL_CONTEXT)
public class LikeOpsController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @GetMapping("/check")
    public RestResult<Boolean> checkRecord(@RequestParam("type") TypeEnum type, @RequestParam("typeId") Long typeId) {
        return RestResultUtils.success(recordService.checkRecord(type, typeId));
    }

    @PostMapping("/like")
    public RestResult<Boolean> giveOrCancelLike(@RequestBody LikeRequestParam requestParam) {
        return RestResultUtils.success(recordService.pushRecordMessage(requestParam.getType(),
                requestParam.getTypeId(), requestParam.getStatus()));
    }
}
