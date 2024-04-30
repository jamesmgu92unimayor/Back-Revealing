package com.delitech.revealing.commons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class RevealingProperties {

    @Value("${api.docs.endpoint}")
    private String endpointUrl;

    // register users test
    @Value("${register.users.test}")
    private Boolean registerUsersTest;
}
