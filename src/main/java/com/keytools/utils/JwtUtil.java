package com.keytools.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.PrivateKey;
import java.time.Instant;
import java.util.Date;

public class JwtUtil {
    public static String getJWTToken(String issuer,
                              String subject,
                              Integer jwtExpInSeconds,
                              PrivateKey privateKey,
                              String kid)
            throws JOSEException {

        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(privateKey);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .issuer(issuer)
                .expirationTime(
                        Date.from(Instant.now().plusSeconds(jwtExpInSeconds)))
                .issueTime(Date.from(Instant.now()))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(kid).build(),
                claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // To serialize to compact form
        String generatedToken = signedJWT.serialize();

        return generatedToken;
    }
}
