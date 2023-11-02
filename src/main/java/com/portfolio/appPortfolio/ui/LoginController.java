package com.portfolio.appPortfolio.ui;

import com.portfolio.appPortfolio.service.JwtService;
import com.portfolio.appPortfolio.ui.model.request.AccountCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredential accountCredential){
        UsernamePasswordAuthenticationToken creds= new UsernamePasswordAuthenticationToken(
                accountCredential.getAdminName(),
                accountCredential.getPassword()
        );
        Authentication auth= authenticationManager.authenticate(creds);
        String jwts= jwtService.getToken(auth.getName());
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer "+jwts).header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").build();
    }


}
