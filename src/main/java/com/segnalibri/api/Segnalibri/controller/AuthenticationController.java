package com.segnalibri.api.Segnalibri.controller;

import com.segnalibri.api.Segnalibri.config.JwtService;
import com.segnalibri.api.Segnalibri.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;


    @PostMapping
    @RequestMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String authenticate(@RequestBody AuthRequest request) throws ResponseStatusException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDetails user = userDetailsService.loadUserByUsername(request.email());

        if (user != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtService.generateToken(user);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error");
    }

    //TODO aggiungere post per registazione
    //TODO creare test per questo controller
}
