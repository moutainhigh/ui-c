package com.sep.common.utils;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class HttpUtil {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        StringBuilder jsonStr = new StringBuilder();
        BufferedReader in = null;
        String urlNameString = "";
        try {
            urlNameString = url + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            // connection.setReadTimeout(120 * 1000);
            // connection.setConnectTimeout(60 * 1000);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            // for (String key : map.keySet()) {
            // System.out.println(key + "--->" + map.get(key));
            // }

            InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                result = new String(buff, 0, length);
                jsonStr.append(result);
            }

            Gson gson = new Gson();
            Map temp = gson.fromJson("", Map.class);

        } catch (Exception e) {
            System.out.println("请求地址:url:" + urlNameString);
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return jsonStr.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        URLConnection conn = null;
        StringBuilder jsonStr = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = realUrl.openConnection();
            conn.setReadTimeout(120 * 1000);
            conn.setConnectTimeout(20000);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应

            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                String result = new String(buff, 0, length);
                jsonStr.append(result);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonStr.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, Map<String, String> header) {
        PrintWriter out = null;
        BufferedReader in = null;
        URLConnection conn = null;
        StringBuilder jsonStr = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = realUrl.openConnection();
            conn.setReadTimeout(120 * 1000);
            conn.setConnectTimeout(20000);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 添加header信息
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应

            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                String result = new String(buff, 0, length);
                jsonStr.append(result);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return jsonStr.toString();
    }

    /*
     * 包的方式 psot传参方式 E代驾专用接口 add cjl
     */
    public static String doPostJson(String url, String param) {
        String responseBody = "";
        HttpPost httppost = new HttpPost(url);
        StringEntity entities = null;
        try {
            entities = new StringEntity(param, "UTF-8");
        } catch (Exception e) {
            System.out.println(e);
        }
        entities.setContentEncoding("UTF-8");
        entities.setContentType("application/x-www-form-urlencoded");
        httppost.setEntity(entities);
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        try {
            responseBody = new DefaultHttpClient().execute(httppost, responseHandler);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            responseBody = "timeout";
            return responseBody;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
            return responseBody;
        }
        return responseBody;
    }

    /*
     * get传参方式 E代驾专用接口 add cjl
     */
    public static String doGetJson(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 作者：崔金龙
     *
     * @param urlPath
     * @param map
     * @return
     * @throws Exception
     */
    public static String postBody(String urlPath, Map<String, Object> map) throws Exception {
        // 定义一个可关闭的httpClient的对象
        CloseableHttpClient httpClient = null;
        // 定义httpPost对象
        HttpPost post = null;
        // 返回结果
        String result = null;
        try {
            // 1.创建httpClient的默认实例
            httpClient = HttpClients.createDefault();
            // 2.提交post
            post = new HttpPost(urlPath);
            if (map != null && map.size() > 0) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(),
                            URLEncoder.encode(entry.getValue().toString(), "utf-8")));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                post.setEntity(entity);
            }
            CloseableHttpResponse response = httpClient.execute(post);
            try {
                if (response != null) {
                    HttpEntity httpEntity = response.getEntity();
                    // 如果返回的内容不为空
                    if (httpEntity != null) {
                        result = EntityUtils.toString(httpEntity);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 关闭response
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭资源
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;

    }

//
//	public static String doPost(String url, Map<String, Object> paramMap) {
//
//		CloseableHttpClient httpClient = null;
//		CloseableHttpResponse httpResponse = null;
//		String result = "";
//		// 创建httpClient实例
//		httpClient = HttpClients.createDefault();
//		// 创建httpPost远程连接实例
//		HttpPost httpPost = new HttpPost(url);
//		// 配置请求参数实例
//		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
//				.setConnectionRequestTimeout(35000)// 设置连接请求超时时间
//				.setSocketTimeout(60000)// 设置读取数据连接超时时间
//				.build();
//		// 为httpPost实例设置配置
//		httpPost.setConfig(requestConfig);
//		// 设置请求头
//		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
//		// 封装post请求参数
//		if (null != paramMap && paramMap.size() > 0) {
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			// 通过map集成entrySet方法获取entity
//			Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
//			// 循环遍历，获取迭代器
//			Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
//			while (iterator.hasNext()) {
//				Map.Entry<String, Object> mapEntry = iterator.next();
//				nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
//			}
//			// 为httpPost设置封装好的请求参数
//			try {
//
//				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		try {
//			// httpClient对象执行post请求,并返回响应参数对象
//			httpResponse = httpClient.execute(httpPost);
//			// 从响应对象中获取响应内容
//			HttpEntity entity = httpResponse.getEntity();
//			result = EntityUtils.toString(entity);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			// 关闭资源
//			if (null != httpResponse) {
//				try {
//					httpResponse.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (null != httpClient) {
//				try {
//					httpClient.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return result;
//	}

}
