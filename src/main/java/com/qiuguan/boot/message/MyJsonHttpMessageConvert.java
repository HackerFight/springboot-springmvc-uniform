package com.qiuguan.boot.message;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.el.lang.ELArithmetic;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author qiuguan
 * @see org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter#readInternal
 * @see org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter#writeInternal
 */
public class MyJsonHttpMessageConvert extends AbstractHttpMessageConverter<Object> {

    public MyJsonHttpMessageConvert() {
        //自定义媒体类型
        super(new MediaType("application", "x-www-form-custom-json"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 读取请求数据
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        // String s = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        JavaType javaType = mapper.constructType(clazz);

        MediaType contentType = inputMessage.getHeaders().getContentType();
        Charset charset = contentType.getCharset();
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }

        if (charset.equals(StandardCharsets.UTF_8)) {
            return mapper.readValue(inputMessage.getBody(), javaType);
        } else {
            Reader reader = new InputStreamReader(inputMessage.getBody(), charset);
            return mapper.readValue(reader, javaType);
        }
    }

    /**
     * 将数据写回去
     * @param o
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //StreamUtils.copy((String)o, StandardCharsets.UTF_8, outputMessage.getBody());
        OutputStream outputStream = StreamUtils.nonClosing(outputMessage.getBody());
        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
        mapper.writeValue(outputStream, o);
        outputStream.close();
    }
}
