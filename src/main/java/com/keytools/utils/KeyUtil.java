package com.keytools.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.security.KeyFactory;
import java.security.cert.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    private static final String KEY_BEGIN_END_REGEX = "-----[A-Z ]+-----";
    private static final String KEY_STORE_FILE_NAME = "KeyStoreFile.jks";
    private static final String PUBLIC_KEY_FILE_NAME = "PublicKeyFile.key";
    private static final String PRIVATE_KEY_FILE_NAME = "PrivateKeyFile.key";
    private static final String PUBLIC_CERTIFICATE_FILE_NAME = "PublicCertFile.crt";


    public static RSAPublicKey readPublicKey() throws Exception {
        return readPublicKey(getPublicKeyFilePath());
    }

    public static RSAPublicKey readPublicKey(Path filePath) throws Exception {
        String key = new String(Files.readAllBytes(filePath), Charset.defaultCharset());

        String publicKeyPEM = key
                .replaceAll(KEY_BEGIN_END_REGEX, "")
                .replaceAll(System.lineSeparator(), "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public static RSAPrivateKey readPrivateKey() throws Exception {
        return readPrivateKey(getPrivateKeyFilePath());
    }

    public static RSAPrivateKey readPrivateKey(Path filePath) throws Exception {
        System.out.println("reading Resource path: " + filePath);
        String key = new String(Files.readAllBytes(filePath), Charset.defaultCharset());

        String privateKeyPEM = key
                .replaceAll(KEY_BEGIN_END_REGEX, "")
                .replaceAll(System.lineSeparator(), "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);


        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    public static X509Certificate getX509Certificate() throws CertificateException, FileNotFoundException {
        return getX509Certificate(getPublicCertificateFilePath());
    }

    public static X509Certificate getX509Certificate(Path filePath) throws CertificateException, FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePath.toString());
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

        return cert;
    }

    public static X509Certificate parseX509Cert() throws CertificateException, IOException {
        String base64CertChain = new String(Files.readAllBytes(getPublicCertificateFilePath()), Charset.defaultCharset());

        String base64CertChainTrimmed = base64CertChain
                .replaceAll(KEY_BEGIN_END_REGEX, "")
                .replaceAll(System.lineSeparator(), "");

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        byte[] certChainBytes = Base64.getDecoder().decode(base64CertChainTrimmed);
        if(null != certChainBytes) {
            return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certChainBytes));
        }
        return null;
    }


    public static Path getResourcesPath() {
        try {
            return Paths.get(KeyUtil.class.getResource(File.separator).toURI());
        } catch (URISyntaxException e) {
            System.out.println("Unable to retrieve the resource path for the KeyUtil class");
        }

        return null;
    }

    public static Path getPublicCertificateFilePath() {
        // System.out.println("Resource path1: " + Paths.get(KeyUtil.class.getResource("/").getPath()));
        return Paths.get(String.format("%s%s%s", getResourcesPath().toAbsolutePath(), File.separator, PUBLIC_CERTIFICATE_FILE_NAME));
    }

    public static Path getPublicKeyFilePath() {
        // System.out.println("Resource path1: " + Paths.get(KeyUtil.class.getResource("/").getPath()));
        return Paths.get(String.format("%s%s%s", getResourcesPath().toAbsolutePath(), File.separator, PUBLIC_KEY_FILE_NAME));
    }

    public static Path getPrivateKeyFilePath() {
        URL url = KeyUtil.class.getClassLoader().getResource("PrivateKeyFile.key");
        System.out.println("Resource path1: " + url.getPath());
        // System.out.println("Resource path1: " + Paths.get(KeyUtil.class.getResource("/").getPath()));
        //return Paths.get(url.getPath());
        return Paths.get("../../../PrivateKeyFile.key");
    }

    public static Path getFilePath(String path) {
        //System.out.println("Resource path:: getFilePath: " + Paths.get(path));
        //System.out.println("Resource path:: getFilePath: " + Paths.get(path).toAbsolutePath());
        return FileSystems.getDefault().getPath(path);
    }

    private static String readKeyFromFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("-----")) {
                sb.append(line);
            }
        }
        br.close();
        return sb.toString();
    }
}
