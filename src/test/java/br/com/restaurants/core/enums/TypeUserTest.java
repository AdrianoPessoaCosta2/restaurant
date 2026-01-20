package br.com.restaurants.core.enums;

import br.com.restaurants.infrastructure.exception.TypeUserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TypeUserTest {

    @Test
    void shouldReturnCorrectCode() {
        assertEquals("C", TypeUser.CUSTOMER.getCode());
        assertEquals("O", TypeUser.RESTAURANT_OWNER.getCode());
    }

    @Test
    void shouldReturnEnumFromValidCode() {
        assertEquals(TypeUser.CUSTOMER, TypeUser.fromCode("C"));
        assertEquals(TypeUser.RESTAURANT_OWNER, TypeUser.fromCode("O"));
    }

    @Test
    void shouldThrowExceptionForInvalidCode() {
        assertThrows(TypeUserException.class, () -> TypeUser.fromCode("X"));
        assertThrows(TypeUserException.class, () -> TypeUser.fromCode(""));
        assertThrows(TypeUserException.class, () -> TypeUser.fromCode(null));
    }
}