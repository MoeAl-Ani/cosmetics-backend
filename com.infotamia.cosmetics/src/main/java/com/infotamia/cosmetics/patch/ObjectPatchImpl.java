package com.infotamia.cosmetics.patch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.RestCoreException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author Mohammed Al-Ani
 */
@SuppressWarnings("unchecked")
public class ObjectPatchImpl implements ObjectPatch {
    private final ObjectMapper mapper;
    private final JsonNode patchNode;

    public ObjectPatchImpl(JsonNode patchNode, ObjectMapper mapper) {
        this.mapper = mapper;
        this.patchNode = patchNode;
    }

    @Override
    public <T> T apply(T target) {
        try {
            JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(patchNode);
            JsonNode jsonNode = mapper.valueToTree(target);
            JsonNode apply = jsonMergePatch.apply(jsonNode);
            ByteArrayOutputStream resultAsByteArray = new ByteArrayOutputStream();
            mapper.writeValue(resultAsByteArray, apply);
            return (T) mapper.readValue(resultAsByteArray.toByteArray(), target.getClass());
        } catch (JsonPatchException | IOException e) {
            throw new RestCoreException(500, BaseErrorCode.OBJECT_PATCH_MAPPING, e.getMessage());
        }
    }
}
