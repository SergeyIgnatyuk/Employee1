package com.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link SecurityService} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Service
public class SecurityServiceImpl implements SecurityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, readOnly = true)
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            LOGGER.debug("Successfully {} auto logged in", username);
        }
    }
}
