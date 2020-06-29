package core;

import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.*;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

/*
    Our simple static class that demonstrates how to create and decode JWTs.
 */
public class JWTGenerator {

    private static String SECRET_KEY = "MIIEvQIBADANBgEXAMPLE=";

    //Sample method to construct a JWT
    public static String createJWT(String rut, Integer user_id) throws NoSuchAlgorithmException, InvalidKeySpecException {

        long ttlMillis = 800000;
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] encodedKey = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);

        final Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");

        ArrayList<String> scope = new ArrayList<>();
        scope.add("read");
        scope.add("write");
        scope.add("trust");
        scope.add("api");

        ArrayList<String> authorities = new ArrayList<>();
        authorities.add("ROLE_USER");

        JwtBuilder builder = Jwts.builder()
                .setHeader(header)
                .setId("test-token-id")
                .claim("user_id", user_id)
                .claim("user_name", rut)
                .claim("scope", scope)
                .claim("authorities", authorities)
                .claim("is_temp_password", false)
                .claim("client_id", "clientId1")
                .signWith(signatureAlgorithm, privKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact().toString();
    }

    public static Claims decodeJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
