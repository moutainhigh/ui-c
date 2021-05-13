package com.sep.distribution.view.excel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sep.distribution.enums.WithdrawStatus;
import com.sep.distribution.vo.background.WithdrawPageSearchVo;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class WithdrawView extends AbstractXlsxView {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<String, String> HEADERS = Maps.newLinkedHashMap();

    static {
        HEADERS.put("ID", "id");
        HEADERS.put("userId", "用户ID");
        HEADERS.put("name", "真实姓名");
        HEADERS.put("phone", "手机号");
        HEADERS.put("amount", "提现金额(元)");
        HEADERS.put("accountType", "提现类型");
        HEADERS.put("account", "提现帐号");
        HEADERS.put("state", "提现状态");
        HEADERS.put("createTime", "申请时间");
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {
        List<WithdrawPageSearchVo> vos = (List<WithdrawPageSearchVo>) model.get("data");
        Sheet sheet = workbook.createSheet();
        buildHeader(sheet);
        buildRow(sheet, vos);
    }

    private void buildHeader(Sheet sheet) {
        List<String> headerNames = Lists.newArrayList(HEADERS.values());
        Row row = sheet.createRow(0);
        for (int i = 0; i < headerNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerNames.get(i));
        }
    }

    private void buildRow(Sheet sheet, List<WithdrawPageSearchVo> vos)
            throws InvocationTargetException, IllegalAccessException {
        List<PropertyDescriptor> targetPds =
                Arrays.stream(BeanUtils.getPropertyDescriptors(WithdrawPageSearchVo.class))
                        .filter(pd -> HEADERS.containsKey(pd.getName())).collect(Collectors.toList());
        for (int i = 1; i <= vos.size(); i++) {
            Row row = sheet.createRow(i);
            WithdrawPageSearchVo vo = vos.get(i - 1);
            buildCells(row, targetPds, vo);
        }
    }

    private void buildCells(Row row, List<PropertyDescriptor> targetPds, WithdrawPageSearchVo vo)
            throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < targetPds.size(); i++) {
            PropertyDescriptor pd = targetPds.get(i);
            Method readMethod = pd.getReadMethod();
            Cell cell = row.createCell(i);
            Object value = readMethod.invoke(vo);
            if (Objects.nonNull(value)) {
                if (pd.getName().equals("state")) {
                    WithdrawStatus withdrawStatus = WithdrawStatus.valueOf((Integer) value);
                    cell.setCellValue(withdrawStatus.getDescription());
                    continue;
                }
                if (pd.getName().equals("createTime")) {
                    cell.setCellValue(formatter.format((LocalDateTime) value));
                    continue;
                }
                cell.setCellValue(value.toString());
            }
        }
    }

}