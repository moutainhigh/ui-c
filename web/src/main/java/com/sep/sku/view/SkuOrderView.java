package com.sep.sku.view;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sep.sku.bean.ExportOrderInfo;
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


public class SkuOrderView extends AbstractXlsxView {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Map<String, String> HEADERS = Maps.newLinkedHashMap();

    static {
        HEADERS.put("createTime","下单时间");
        HEADERS.put("orderAmount", "付款金额/积分");
        HEADERS.put("orderNo", "订单号");
        HEADERS.put("orderSkus", "订单商品");
        HEADERS.put("orderStatus", "订单状态");
        HEADERS.put("payway", "支付类型");
        HEADERS.put("userId", "买家id");
    }


    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response)
            throws InvocationTargetException, IllegalAccessException {
        List<ExportOrderInfo> orderVos = (List<ExportOrderInfo>) model.get("data");
        Sheet sheet = workbook.createSheet();
        buildHeader(sheet);
        buildRow(sheet, orderVos);
    }

    private void buildHeader(Sheet sheet) {
        List<String> headerNames = Lists.newArrayList(HEADERS.values());
        Row row = sheet.createRow(0);
        for (int i = 0; i < headerNames.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerNames.get(i));
        }
    }

    private void buildRow(Sheet sheet, List<ExportOrderInfo> orderVos)
            throws InvocationTargetException, IllegalAccessException {
        List<PropertyDescriptor> targetPds =
                Arrays.stream(BeanUtils.getPropertyDescriptors(ExportOrderInfo.class))
                        .filter(pd -> HEADERS.containsKey(pd.getName())).collect(Collectors.toList());
        for (int i = 1; i <= orderVos.size(); i++) {
            Row row = sheet.createRow(i);
            ExportOrderInfo vo = orderVos.get(i - 1);
            buildCells(row, targetPds, vo);
        }
    }

    private void buildCells(Row row, List<PropertyDescriptor> targetPds, ExportOrderInfo vo)
            throws InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < targetPds.size(); i++) {
            PropertyDescriptor pd = targetPds.get(i);
            Method readMethod = pd.getReadMethod();
            Cell cell = row.createCell(i);
            Object value = readMethod.invoke(vo);
            if (Objects.nonNull(value)) {
                if(pd.getName().equals("createTime")){
                    cell.setCellValue(formatter.format((LocalDateTime) value));
                    continue;
                }
                cell.setCellValue(value.toString());
            }
        }
    }

}
