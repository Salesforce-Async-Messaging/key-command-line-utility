package com.keytools.utils;

import com.keytools.resources.JWKey;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.util.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Objects;


public class JwkUtil {

    public static JWKey getJWK(PublicKey publicKey,
                               String kid,
                               X509Certificate x509Certificate)
            throws CertificateEncodingException {

        RSAPublicKey publicRsaKey = (RSAPublicKey) publicKey;
        return getJWK(publicRsaKey, kid, x509Certificate);
    }

    public static JWKey getJWK(RSAPublicKey publicRsaKey,
                               String kid,
                               X509Certificate x509Certificate)
            throws CertificateEncodingException {

        RSAKey jwk = new RSAKey.Builder(publicRsaKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .x509CertChain(Arrays.asList(Base64.encode(
                        x509Certificate.getEncoded())))
                .keyID(kid)
                .build();

        return convertRsaKeyToJWKey(jwk);
    }

    private static JWKey convertRsaKeyToJWKey(RSAKey rsaKey) {
        JWKey jwKey = new JWKey(rsaKey.getKeyID(),
                Objects.toString(rsaKey.getAlgorithm(), null),
                rsaKey.getKeyUse().toString(),
                rsaKey.getKeyType().toString(),
                Objects.toString(rsaKey.getX509CertURL(), null),
                Objects.toString(rsaKey.getX509CertSHA256Thumbprint(), null),
                rsaKey.getX509CertChain(),
                "y",
                Objects.toString(rsaKey.getModulus(), null),
                Objects.toString(rsaKey.getPublicExponent(), null),
                "crv", "d", "k");
        return jwKey;
    }

}
