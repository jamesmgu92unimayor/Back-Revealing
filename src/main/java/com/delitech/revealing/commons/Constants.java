package com.delitech.revealing.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String TIME_ZONE = "America/Bogota";
    public static final String GENERAL_SUCCESS = "general.success";
    public static final String GENERAL_LIST_SUCCESS = "general.list.success";
    public static final String GENERAL_GET_SUCCESS = "general.get.success";
    public static final String GENERAL_CREATE_SUCCESS = "general.create.success";
    public static final String GENERAL_UPDATE_SUCCESS = "general.update.success";
    public static final String GENERAL_DELETE_SUCCESS = "general.delete.success";
    public static final String NO_USERS_REGISTER = "exception.model.no.users";
    public static final String GENERAL_TRANSACTION_SUCCESS = "general.transaction.success";

    public static final String GENERAL_GET_MICROCHIP_SUCCESS = "general.get.microchip.valid";

    public static final String EXCEPTION_MODEL_NOTFOUND = "exception.model.notfound";
    public static final String EXCEPTION_MODEL_ERRORACTION = "exception.model.error.action";
    public static final String EXCEPTION_MODEL_EMAIL = "exception.model.email";
    public static final String EXCEPTION_MODEL_DOCUMENT = "exception.model.document";
    public static final String EXCEPTION_MODEL_PASS = "exception.model.pass";
    public static final String EXCEPTION_MODEL_PASS_LENGTH = "exception.model.pass.length";
    public static final String EXCEPTION_MODEL_USER_INVALID = "exception.model.user.invalid";
    public static final String EXCEPTION_MODEL_CODE_ACTIVE = "exception.model.code.active";
    public static final String EXCEPTION_MODEL_CODE_INVALID = "exception.model.code.invalid";
    public static final String EXCEPTION_MODEL_PASS_INVALID = "exception.model.pass.invalid";
    public static final String EXCEPTION_MODEL_DAY_INVALID = "exception.model.day.invalid";
    public static final String EXCEPTION_MODEL_MONTH_INVALID = "exception.model.month.invalid";

    public static final String EXCEPTION_MODEL_USER_CARD = "exception.model.user.card";

    public static final String EXCEPTION_PLAN_NOTFOUND = "exception.model.plan.notfound";
    public static final String EXCEPTION_PLAN_TRIAL= "exception.model.plan.trial";
    public static final String EXCEPTION_PAYU_ERROR = "exception.model.payu.error";
    public static final String EXCEPTION_PAYU_SIGN_ERROR = "exception.model.payu.sign.error";
    public static final String EXCEPTION_PLAN_ACTIVE = "exception.model.plan.active";
    public static final String EXCEPTION_CARD_USER_INVALID = "exception.model.card.user.invalid";

    public static final String EXCEPTION_CARD_USER_EXIST = "exception.model.card.user.exist";
    public static final String EXCEPTION_ATTACHMENT_USERS_LIMIT = "exception.attachment.users.limit";
    public static final String EXCEPTION_ATTACHMENT_USERS = "exception.attachment.users";

    public static final String EXCEPTION_PET_TYPE_NOTFOUND = "exception.pet.type.notfound";
    public static final String EXCEPTION_PET_BREED_NOTFOUND = "exception.pet.breed.notfound";
    public static final String EXCEPTION_PET_SIZE_NOTFOUND = "exception.pet.size.notfound";
    public static final String EXCEPTION_PET_BIRTHDATE_REQUIRED = "exception.pet.birthdate.required";
    public static final String EXCEPTION_PET_MICROCHIP_NUMBER = "exception.pet.microchip.number";
    public static final String EXCEPTION_PET_MICROCHIP_INUSE = "exception.pet.microchip.inuse";
    public static final String EXCEPTION_PET_DOCUMENT_INVALID = "exception.pet.document.invalid";
    public static final String EXCEPTION_FILE_FORMAT_INVALID = "exception.file.format.invalid";
    public static final String EXCEPTION_FILE_NOTFOUND = "exception.file.notfound";
    public static final String EXCEPTION_PET_IMG_UUID = "exception.pet.img.uuid";
    public static final String EXCEPTION_PET_NOT_FOUND = "exception.pet.not.found";
    public static final String EXCEPTION_PET_LOST_ALREADY_EXIST = "exception.pet.lost.already.exist";
}
