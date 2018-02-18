package com.danieleautizi.utility;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MongoUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MongoUtils.class);

    public static ObjectId stringToObject(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException var2) {
            LOG.debug("String {} is not a type of ObjectId", id, var2);
            return null;
        }
    }

    public static Boolean isStringObjectId(String id) {
        try {
            if (id != null) {
                new ObjectId(id);
                return true;
            }
        } catch (IllegalArgumentException var2) {
            LOG.debug("String {} is not a type of ObjectId", id, var2);
        }

        return false;
    }

    public static Boolean isValidListOfObjectIds(List<String> ids) {
        return ids == null ? false : ids.stream().allMatch((id) -> {
            return id != null && ObjectId.isValid(id);
        });
    }

    public static List<ObjectId> stringToObject(List<String> ids) {
        List<ObjectId> objectIds = new ArrayList();
        if (ids != null) {
            Iterator var2 = ids.iterator();

            while(var2.hasNext()) {
                String id = (String)var2.next();

                try {
                    ObjectId oid = new ObjectId(id);
                    objectIds.add(oid);
                } catch (IllegalArgumentException var5) {
                    LOG.debug("String {} is not a type of ObjectId", id, var5);
                }
            }
        }

        return objectIds;
    }

    public static ObjectId castToObjectId(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof ObjectId) {
            return (ObjectId)obj;
        } else {
            LOG.error("Object {} is not a type of ObjectId", obj);
            throw new IllegalArgumentException("Object {} is not a type of ObjectId");
        }
    }

    public static List<String> toHexStrings(List<ObjectId> ids) {
        return (List)(ids == null ? new ArrayList() : (List)ids.stream().map(ObjectId::toHexString).collect(Collectors.toList()));
    }

    public static String stringOrNull(ObjectId objectId) {
        return objectId == null ? null : objectId.toHexString();
    }

    public static ObjectId objectIdOrNull(final String id) {

        return id == null
               ? null
               : MongoUtils.stringToObject(id);
    }
}
