package com.delitech.revealing.commons;

public enum UserTypeEnum {

    ADMIN("ADMIN"),
    CLIENT("CLIENT"),
    RESTAURANT("RESTAURANT");

    private final String userType;

    UserTypeEnum(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
