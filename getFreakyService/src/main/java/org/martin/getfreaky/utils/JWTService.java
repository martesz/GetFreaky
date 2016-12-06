/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.getfreaky.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.Singleton;

/**
 *
 * @author martin
 *
 * Creates and validates JSON Web Tokens.
 *
 */
@Singleton
public class JWTService {

    // Secret key generated only once in the constructor
    private final Key key;

    public JWTService() {
        super();
        key = MacProvider.generateKey();
    }
    
    /**
     *
     * @param userId This is going to be the subject of the JWT
     * @return JWT compacted into a String form
     */
    public String issueToken(String userId) {
        String compactJWT = Jwts.builder()
                .setSubject(userId)
                .setExpiration(oneWeekLater())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return compactJWT;
    }

    /**
     *
     * @param compactJWT
     * @return The subject of the JWT which is the userId
     */
    public String validate(String compactJWT) {
        String userId = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(compactJWT)
                .getBody()
                .getSubject();
        return userId;
    }
    
    private Date oneWeekLater(){
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_MONTH, 1);
        return c.getTime();
    }

}
