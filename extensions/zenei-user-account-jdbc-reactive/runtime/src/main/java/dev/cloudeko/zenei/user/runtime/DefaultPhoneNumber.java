package dev.cloudeko.zenei.user.runtime;

import dev.cloudeko.zenei.user.PhoneNumber;

public class DefaultPhoneNumber extends PhoneNumber {

    public DefaultPhoneNumber() {
    }

    public DefaultPhoneNumber(String phoneNumber, boolean verified, boolean primary) {
        super(phoneNumber, verified, primary);
    }
}
