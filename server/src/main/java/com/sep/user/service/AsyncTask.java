package com.sep.user.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.sep.common.utils.HttpUtil;
import com.sep.common.utils.NetworkUtil;
import com.sep.distribution.dto.BaseUpdateDto;
import com.sep.distribution.dto.DistributionApplyDto;
import com.sep.distribution.service.ApplyService;
import com.sep.distribution.service.IdentityService;
import com.sep.sku.tool.TencentCos;
import com.sep.user.model.Area;
import com.sep.user.model.Facility;
import com.sep.user.model.WxUser;
import com.sep.user.repository.WxUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AsyncTask {


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private WxUserMapper wxUserMapper;

    @Value("${wx.getwxacodeunlimit}")
    private String getwxacodeunlimit;

    @Value("${wx.saveImgPath}")
    private String saveImgPath;

    @Autowired
    private AreaService areaService;

    @Autowired
    private FacilityService facilityService;

    @Value("${baidu.ak}")
    String baiduAk;

    @Value("${baidu.api}")
    String baiduApi;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private IdentityService identityService;


    @Autowired
    private TencentCos tencentCos;


    @Value("${token.prefix}")
    private String tokenPrefix;

    @Async
    public void generateQRCode(Integer userId) {
        PrintWriter printWriter = null;
        BufferedInputStream bis = null;
        try {
            log.info("开始生成用户二维码-----------------");
            log.info("获取的access_token----------------->{}", redisTemplate.opsForValue().get(tokenPrefix));
            URL url = new URL(getwxacodeunlimit.replaceAll("ACCESS_TOKEN", redisTemplate.opsForValue().get(tokenPrefix)));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", userId);
            paramJson.put("page", "pages/home/home");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            String contentType = httpURLConnection.getContentType();
            if (contentType.contains("json")) {
                log.info("调用微信小程序生成接口出错,token失效---->{}", contentType);
            } else {
                bis = new BufferedInputStream(httpURLConnection.getInputStream());
                String time = System.currentTimeMillis() + ".png";
                String path = saveImgPath + time;
                log.info(path);
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                    log.info("创建文件成功----------------->{}", path);
                }
                OutputStream os = new FileOutputStream(new File(path));
                int len;
                byte[] arr = new byte[1024];
                while ((len = bis.read(arr)) != -1) {
                    os.write(arr, 0, len);
                    os.flush();
                }
                log.info("文件写入完成-----------------");
                os.close();
                String key = System.currentTimeMillis() + "_" + userId + ".png";
                String fileUrl = tencentCos.SimpleUploadFileFromLocal(key, path);
                log.info("对象存储后返回结果----------------->{}", fileUrl);
                WxUser user = wxUserMapper.selectById(userId);
                user.setInviteQrCode(fileUrl);
                wxUserMapper.updateById(user);
                log.info("更新用户----------------->{}", user);
            }
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(Throwables.getStackTraceAsString(e));
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
        }

    }

    public Integer add() {
        File file = new File(saveImgPath);
        return file.mkdir() ? 1 : 0;
    }

    public String generateQRCodeBySku(Integer userId, Integer skuId) {
        PrintWriter printWriter = null;
        BufferedInputStream bis = null;
        try {
            log.info("开始生成用户二维码-----------------");
            log.info("获取的access_token----------------->{}", redisTemplate.opsForValue().get(tokenPrefix));
            URL url = new URL(getwxacodeunlimit.replaceAll("ACCESS_TOKEN", redisTemplate.opsForValue().get(tokenPrefix)));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", userId + "@" + skuId);
            paramJson.put("page", "pages/storeList/storeListDetail");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            String contentType = httpURLConnection.getContentType();
            if (contentType.contains("json")) {
                log.info("调用微信小程序生成接口出错,token失效---->{}", contentType);
            } else {
                bis = new BufferedInputStream(httpURLConnection.getInputStream());
                String time = System.currentTimeMillis() + ".png";
                String path = saveImgPath + time;
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                    log.info("创建文件成功----------------->{}", path);
                }

                OutputStream os = new FileOutputStream(new File(path));
                int len;
                byte[] arr = new byte[1024];
                while ((len = bis.read(arr)) != -1) {
                    os.write(arr, 0, len);
                    os.flush();
                }
                log.info("文件写入完成-----------------");
                os.close();
                String key = System.currentTimeMillis() + "_" + userId + "_" + skuId + ".png";
                String fileUrl = tencentCos.SimpleUploadFileFromLocal(key, path);
                log.info("云存储返回路径 --------->{}", fileUrl);
                return fileUrl;
            }
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(Throwables.getStackTraceAsString(e));
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return null;

    }

    public String admissionCodeBySku(Integer skuId) {
        PrintWriter printWriter = null;
        BufferedInputStream bis = null;
        try {
            log.info("开始生成用户二维码-----------------");
            log.info("获取的access_token----------------->{}", redisTemplate.opsForValue().get(tokenPrefix));
            URL url = new URL(getwxacodeunlimit.replaceAll("ACCESS_TOKEN", redisTemplate.opsForValue().get(tokenPrefix)));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数"pages/signIn/signIn",
            JSONObject paramJson = new JSONObject();
            paramJson.put("scene", skuId);
            paramJson.put("page", "pages/signIn/signIn");
            paramJson.put("width", 430);
            paramJson.put("auto_color", true);
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            String contentType = httpURLConnection.getContentType();
            log.info(contentType);
            if (contentType.contains("json")) {
                log.info("调用微信小程序生成接口出错,token失效---->{}", contentType);
            } else {
                bis = new BufferedInputStream(httpURLConnection.getInputStream());
                String time = System.currentTimeMillis() + ".png";
                log.info(time);
                String path = saveImgPath + time;
                log.info(path);
                File file = new File(path);
                if (!file.exists()) {
                    log.info(path);
                    file.createNewFile();
                    log.info("创建文件成功----------------->{}", path);
                }
                OutputStream os = new FileOutputStream(new File(path));
                int len;
                byte[] arr = new byte[1024];
                while ((len = bis.read(arr)) != -1) {
                    os.write(arr, 0, len);
                    os.flush();
                }
                log.info("文件写入完成-----------------");
                os.close();
                String key = System.currentTimeMillis() + skuId + ".png";
                String fileUrl = tencentCos.SimpleUploadFileFromLocal(key, path);
                log.info("云存储返回路径 --------->{}", fileUrl);
                return fileUrl;
            }
        } catch (Exception e) {
            log.error(Throwables.getStackTraceAsString(e));
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(Throwables.getStackTraceAsString(e));
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
        }
        return null;

    }

    @Async
    public void synchronousAreaCode(Integer userId, HttpServletRequest request) {

        try {
            String ip = NetworkUtil.getIpAddress(request);
            Map<String, Object> map = new HashMap<>();
            map.put("ip", ip);
            map.put("ak", baiduAk);
            map.put("coor", "bd09ll");
            String param = "?ip=" + ip + "&ak=" + baiduAk;
            String location = HttpUtil.sendPost(baiduApi, param);
            if (StringUtils.isBlank(location)) {
                log.info("baidu api 接口 无返回数据 ");
                return;
            } else {
                JSONObject json = JSONObject.parseObject(location);
                if (!json.getInteger("status").equals(0)) {
                    log.info("baidu api 调用接口错误............ ");
                    return;
                }
                if (json.containsKey("content")) {
                    Facility facility = new Facility();
                    JSONObject content = json.getJSONObject("content");
                    if (content.containsKey("point")) {
                        JSONObject point = content.getJSONObject("point");
                        facility.setLocalX(point.getString("x"));
                        facility.setLocalY(point.getString("y"));
                    }
                    if (content.containsKey("address_detail")) {
                        JSONObject address_detail = content.getJSONObject("address_detail");
                        log.info("获取的城市信息------------->{}", address_detail.getString("city"));
                        Area area = areaService.getAreaCode(address_detail.getString("city"));
                        if (area != null) {
                            facility.setProvince(address_detail.getString("province"));
                            facility.setCity(address_detail.getString("city"));
                            facility.setAreaCode(area.getId().toString());
                            facility.setUserId(userId);
                            facilityService.save(facility);
                            log.info("执行完成 ----->{} ", json);

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void apply(Integer userId) {
        DistributionApplyDto dto = new DistributionApplyDto();
        dto.setUserId(userId);
        dto.setName("自动申请分销");
        dto.setIdCard("0000000000000000");
        dto.setDistributionIdentityId(identityService.get().getId());
        dto.setIdCardFrontView("自动申请分销");
        dto.setIdCardBackVision("自动申请分销");
        Integer id = applyService.apply(dto);

        BaseUpdateDto baseUpdateDto = new BaseUpdateDto();
        baseUpdateDto.setId(id);
        baseUpdateDto.setUpdateUid("1");

        applyService.approve(baseUpdateDto);

    }

}