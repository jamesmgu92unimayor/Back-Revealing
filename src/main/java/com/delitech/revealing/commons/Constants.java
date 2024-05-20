package com.delitech.revealing.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String TIME_ZONE = "America/Bogota";
    public static final String GENERAL_SUCCESS = "general.success";
    public static final String GENERAL_LIST_SUCCESS = "general.list.success";
    public static final String GENERAL_CREATE_SUCCESS = "general.create.success";
    public static final String GENERAL_UPDATE_SUCCESS = "general.update.success";
    public static final String GENERAL_DELETE_SUCCESS = "general.delete.success";

    public static final String EXCEPTION_MODEL_NOTFOUND = "exception.model.notfound";
    public static final String EXCEPTION_NOT_MATCH = "exception.not.match";
    public static final String EXCEPTION_MODEL_EMAIL = "exception.model.email";
    public static final String EXCEPTION_MODEL_USER_INVALID = "exception.model.user.invalid";
}
