package com.delitech.revealing.config.filters;

import com.delitech.revealing.exception.ObfuscationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class ObfuscationFilter implements Filter {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "Jfd2Uxj1moz4sA5gou+qAhXLr8U47z3PqQPdOvI6vvRGHBBz9UvYoHvCoafOG4wx";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().contains("checkout-payment-v2") || request.getRequestURI().contains("/card-tkn/register-v2")) {
            DecryptingRequestWrapper decryptingRequestWrapper = new DecryptingRequestWrapper(request);

            filterChain.doFilter(decryptingRequestWrapper, response);
        } else {
            filterChain.doFilter(request, servletResponse);
        }
    }

    private static class DecryptingRequestWrapper extends HttpServletRequestWrapper {

        private final String body;

        public DecryptingRequestWrapper(HttpServletRequest request) throws JsonProcessingException {
            super(request);
            StringBuilder stringBuilder = new StringBuilder();

            try (InputStream inputStream = request.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                // Manejar la excepción si ocurre algún error de lectura
                throw new ObfuscationException("Error al leer el cuerpo del mensaje - " + e);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(stringBuilder.toString());
            JsonNode cardNode = jsonNode.get("creditCard");
            String creditCardData = cardNode.asText();

            String decriptedCard = decrypt(creditCardData);
            JsonNode creditCardNode = objectMapper.readTree(decriptedCard);
            ((ObjectNode) jsonNode).set("creditCard", creditCardNode);

            body = jsonNode.toString();
        }

        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
            return new ServletInputStream() {
                public int read() {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                    throw new ObfuscationException("Operation not supported");
                }
            };
        }
    }

    public static SecretKeySpec generateKey() throws NoSuchAlgorithmException {
        byte[] keyBytes = KEY.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    private static String encrypt(String data) {
        try {
            SecretKeySpec keySpec = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new ObfuscationException("Error al encriptar los datos - " + e);
        }

    }

    public static String decrypt(String encryptedText) {
        try {
            SecretKeySpec keySpec = generateKey();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new ObfuscationException("Error al desencriptar los datos - " + e);
        }

    }
}
