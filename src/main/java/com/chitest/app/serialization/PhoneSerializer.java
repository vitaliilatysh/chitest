package com.chitest.app.serialization;

import com.chitest.app.entities.Phone;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PhoneSerializer extends JsonSerializer<Set<Phone>> {

    @Override
    public void serialize(Set<Phone> value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        List<String> phones = new ArrayList<>();

        for(Phone phone: value){
            phones.add(phone.getPhone());
        }

        jgen.writeStartArray();
        for (String phone : phones) {
            jgen.writeString(phone);
        }
        jgen.writeEndArray();
    }

}

