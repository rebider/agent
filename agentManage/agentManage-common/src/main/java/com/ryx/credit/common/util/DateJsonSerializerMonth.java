package com.ryx.credit.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cx on 2018/5/28.
 */
public class DateJsonSerializerMonth extends JsonSerializer<Date> {
    public static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(format.format(date));
    }
}
