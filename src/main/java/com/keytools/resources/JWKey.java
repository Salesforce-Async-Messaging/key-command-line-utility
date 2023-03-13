package com.keytools.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.util.Base64;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JWKey {
    @JsonProperty("kid")
    protected String kid = null;
    @JsonProperty("alg")
    protected String alg = null;
    @JsonProperty("use")
    protected String use = null;
    @JsonProperty("kty")
    protected String kty = null;
    @JsonProperty("x5u")
    protected String x5u = null;
    @JsonProperty("x5t")
    protected String x5t = null;
    @JsonProperty("x5c")
    protected List<String> x5c = null;
    @JsonProperty("y")
    protected String y = null;
    @JsonProperty("n")
    protected String n = null;
    @JsonProperty("e")
    protected String e = null;
    @JsonProperty("crv")
    protected String crv = null;
    @JsonProperty("d")
    protected String d = null;
    @JsonProperty("k")
    protected String k = null;

    public String getKid() {
        return kid;
    }

    public String getAlg() {
        return alg;
    }

    public String getUse() {
        return use;
    }

    public String getKty() {
        return kty;
    }

    public String getX5u() {
        return x5u;
    }

    public String getX5t() {
        return x5t;
    }

    public List<String> getX5c() {
        return x5c;
    }

    public String getY() {
        return y;
    }

    public String getN() {
        return n;
    }

    public String getE() {
        return e;
    }

    public String getCrv() {
        return crv;
    }

    public String getD() {
        return d;
    }

    public String getK() {
        return k;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public JWKey() {
    }

    public JWKey(String kid, String alg, String use, String kty, String x5u,
                 String x5t,
                 List<Base64> x5c,
                 String y,
                 String n, String e,
                 String crv,
                 String d,
                 String k) {
        this.kid = kid;
        this.alg = alg;
        this.use = use;
        this.kty = kty;
        this.x5u = x5u;
        this.x5t = x5t;
        this.y = y;
        this.n = n;
        this.e = e;
        this.crv = crv;
        this.d = d;
        this.k = k;

        List<String> certs = new ArrayList<>();
        for(Base64 base64 : x5c) {
            certs.add(base64.toString());
        }

        this.x5c = certs;
    }
}