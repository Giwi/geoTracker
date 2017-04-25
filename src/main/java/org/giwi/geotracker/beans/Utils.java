package org.giwi.geotracker.beans;

import io.vertx.core.json.JsonObject;
import org.giwi.geotracker.exception.BusinessException;

/**
 * The interface Utils.
 */
public interface Utils {
    /**
     * Test mandatory fields.
     *
     * @param body   the body
     * @param fields the fields
     * @throws BusinessException the business exception
     */
    void testMandatoryFields(JsonObject body, String... fields) throws BusinessException;
}
