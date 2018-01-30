package cn.linzs.app.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author linzs
 * @Date 2018-01-30 15:42
 * @Description
 */
@Configuration
public class WebConfig {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @PostConstruct
    public void initEditableValidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new DateConverter());
        }
    }

    class DateConverter implements Converter<String, Date> {
        private final List<String> formarts = new ArrayList<>(4);

        public DateConverter() {
            formarts.add("yyyy-MM");
            formarts.add("yyyy-MM-dd");
            formarts.add("yyyy-MM-dd hh:mm");
            formarts.add("yyyy-MM-dd hh:mm:ss");
        }

        @Override
        public Date convert(String source) {
            if(source == null || "".equals(source.trim())) {
                return null;
            }

            source = source.trim();
            if(source.matches("^\\d{4}-\\d{1,2}$")) {
                return parseDate(source, formarts.get(0));
            } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                return parseDate(source, formarts.get(1));
            } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
                return parseDate(source, formarts.get(2));
            } else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                return parseDate(source, formarts.get(3));
            } else {
                return parseTimestamp(source);
            }
        }

        /**
         * 格式化日期
         * @param dateStr String 字符型日期
         * @param format String 格式
         * @return Date 日期
         */
        public Date parseDate(String dateStr, String format) {
            Date date = null;
            try {
                DateFormat dateFormat = new SimpleDateFormat(format);
                date = dateFormat.parse(dateStr);
            } catch (Exception e) { }
            return date;
        }

        /**
         * 格式化日期
         * @param dateStr String 类型的时间戳
         * */
        public Date parseTimestamp(String dateStr) {
            try {
                return new Date(Long.valueOf(dateStr));
            } catch (Exception e) {}
            return null;
        }

    }

}
