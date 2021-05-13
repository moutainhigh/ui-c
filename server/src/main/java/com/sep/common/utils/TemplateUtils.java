package com.sep.common.utils;

import com.sep.common.exceptions.SepCustomException;
import com.sep.message.enums.BizErrorCode;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateUtils {

    public static String processLetterTemplate(String letterTemplate, Map<String, Object> messages) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        cfg.setTemplateLoader(templateLoader);
        cfg.setDefaultEncoding("UTF-8");
        StringWriter stringWriter = new StringWriter();
        Template template = null;
        try {
            template = new Template("LetterTemplate", letterTemplate, cfg);
            template.process(messages, stringWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            throw new SepCustomException(BizErrorCode.LETTER_TEMPLATE_PROCESS_ERROR);
        }
        return stringWriter.toString();
    }

}
