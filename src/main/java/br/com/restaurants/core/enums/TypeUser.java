package br.com.restaurants.core.enums;

import br.com.restaurants.exception.TypeUserException;

public enum TypeUser {
    CUSTOMER("C"),
    RESTAURANT_OWNWE("O");

    private String code;

    TypeUser(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TypeUser fromCode(String code) {
        for (TypeUser type : TypeUser.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new TypeUserException("TypeUser not found for: " + code);
    }
}
