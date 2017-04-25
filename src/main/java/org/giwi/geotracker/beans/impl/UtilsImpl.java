package org.giwi.geotracker.beans.impl;

import io.vertx.core.json.JsonObject;
import org.giwi.geotracker.beans.Utils;
import org.giwi.geotracker.exception.BusinessException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Utils.
 */
public class UtilsImpl implements Utils {
    @Override
    public void testMandatoryFields(JsonObject body, String... fields) throws BusinessException {
        final List<String> missingFields =  Arrays.stream(fields).filter(f->!body.containsKey(f)).collect(Collectors.toList());
        if (!missingFields.isEmpty()) {
            throw new BusinessException("Missing mandatory parameters : " + missingFields, 400);
        }
    }
}
