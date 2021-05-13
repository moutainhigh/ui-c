package com.sep.distribution.validator;

import com.sep.distribution.dto.WithdrawApplyDto;
import com.sep.distribution.enums.BizErrorCode;
import com.sep.distribution.service.SettingService;
import com.sep.distribution.service.impl.WithdrawServiceImpl;
import com.sep.common.exceptions.SepCustomException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 返现申请校验器
 * </p>
 *
 * @author litao
 * @since 2020-01-12
 */
@Component
public class WithdrawApplyValidator {

    @Resource
    private WithdrawServiceImpl withdrawService;
    @Resource
    private SettingService settingService;

    public void validator(WithdrawApplyDto dto) {
        BigDecimal minWithdraw = settingService.getMinWithdraw();
        if (minWithdraw.compareTo(dto.getAmount()) > 0) {
            throw new SepCustomException(BizErrorCode.MINWITHDRAW_ERROR.getCode(),
                    String.format(BizErrorCode.MINWITHDRAW_ERROR.getMessage(), minWithdraw.toString()));
        }
        BigDecimal available = withdrawService.available(dto.getUserId());
        int compare = available.compareTo(dto.getAmount());
        if (compare < 0) {
            throw new SepCustomException(BizErrorCode.WITHDRAW_AVAILABLE_LACK);
        }
    }

}