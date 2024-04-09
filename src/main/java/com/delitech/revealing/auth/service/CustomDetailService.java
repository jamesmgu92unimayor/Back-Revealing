package com.delitech.revealing.auth.service;

import com.delitech.revealing.commons.MessageComponent;
import com.delitech.revealing.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageComponent messageComponent;

    public UserDetails loadUserByUsernameAndType(String username, String type) throws UsernameNotFoundException {
        return userRepository.findByEmailAndType(username, type)
                .orElseThrow(() -> new UsernameNotFoundException(messageComponent.getBundleMessage("auth.user.not.found", username)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException(messageComponent.getBundleMessage("general.method.not.configured"));
    }
}
