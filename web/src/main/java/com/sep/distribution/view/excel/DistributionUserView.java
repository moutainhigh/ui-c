package com.sep.distribution.view.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sep.distribution.vo.background.DistributionUserVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistributionUserView extends AbstractXlsxView {

    private static final Map<String, String> HEADERS = Maps.newLinkedHashMap();

    static {
        HEADERS.put("ID", "id");
        HEADERS.put("userId", "用户ID");
        HEADERS.put("name", "真实姓名");
        HEADERS.put("idCard", "身份证号");
        HEADERS.put("distributionIdentity", "分销角色");
        HEADERS.put("stairFansCount", "一级粉丝量");
        HEADERS.put("secondLevelFansCount", "二级粉丝量");
        HEADERS.put("addUpCashBack", "返利总金额(元)");
        HEADERS.put("balance", "账户余额(元)");
        HEADERS.put("addUpExpense", "个人消费");
        HEADERS.put("inviterId", "邀请人");
    }


    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {
        List<DistributionUserVo> distributionUserVos = (List<DistributionUserVo>) model.get("data");
        Sheet sheet = workbook.createSheet();
        buildHeader(sheet);
        buildRow(sheet, distributionUserVos);
    }

    private void buildHeader(Sheet sheet) {
        List<String> headerNames = Lists.newArrayList(HEADERS.values());
        Row row = sheet.createRow(0);
        for (int i = 0; i < headerNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerNames.get(i));
        }
    }

    private void buildRow(Sheet sheet, List<DistributionUserVo> distributionUserVos)
            throws InvocationTargetException, IllegalAccessException {
        List<PropertyDescriptor> targetPds =
                Arrays.stream(BeanUtils.getPropertyDescriptors(DistributionUserVo.class))
                        .filter(pd -> HEADERS.containsKey(pd.getName())).collect(Collectors.toList());
        for (int i = 1; i <= distributionUserVos.size(); i++) {
            Row row = sheet.createRow(i);
            DistributionUserVo vo = distributionUserVos.get(i - 1);
            buildCells(row, targetPds, vo);
        }
    }

    private void buildCells(Row row, List<PropertyDescriptor> targetPds, DistributionUserVo vo)
            throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < targetPds.size(); i++) {
            PropertyDescriptor pd = targetPds.get(i);
            Method readMethod = pd.getReadMethod();
            Cell cell = row.createCell(i);
            Object value = readMethod.invoke(vo);
            if (Objects.nonNull(value)) {
                cell.setCellValue(value.toString());
            }
        }
    }

}