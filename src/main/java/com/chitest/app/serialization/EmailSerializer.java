package com.chitest.app.serialization;

import com.chitest.app.entities.Email;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EmailSerializer extends JsonSerializer<Set<Email>> {

    @Override
    public void serialize(Set<Email> value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        List<String> emails = new ArrayList<>();

        for(Email email: value){
            emails.add(email.getEmail());
        }

        jgen.writeStartArray();
        for (String email : emails) {
            jgen.writeString(email);
        }
        jgen.writeEndArray();
    }

}

