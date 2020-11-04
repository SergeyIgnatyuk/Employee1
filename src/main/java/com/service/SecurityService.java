package com.service;

/**
 * Service interface for Security.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
