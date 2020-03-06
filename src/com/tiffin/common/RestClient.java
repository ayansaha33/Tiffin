package com.tiffin.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Base client to invoke external service. It will be extended by all other clients to have common functionalities.
 */
public abstract class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    @Autowired
    private RestTemplate        itsRestTemplate;

    /**
     * HTTP DELETE Service
     *
     * @param inURI the URI of the requesting service
     * @param inHeaders the header parameters to be send in HTTP header of the request
     * @param inRequest the request object to be send in HTTP body of the request
     * @param inResponseType the response type of HTTP response
     * @param inUrlVariables the URL variables to be replaced in the URI's place holders
     * @param <S> the request object type
     * @param <T> the response object type
     * @return T the response object
     */
    public <S, T> T deleteService(final String inURI, final MultiValueMap<String, String> inHeaders, final S inRequest, final ParameterizedTypeReference<T> inResponseType, final Object... inUrlVariables) {
        return callService(HttpMethod.DELETE, inURI, inHeaders, inRequest, inResponseType, inUrlVariables);
    }

    /**
     * HTTP GET Service call
     *
     * @param inURI the URI of the requesting service
     * @param inHeaders the header parameters to be send in HTTP header of the request
     * @param inRequest the request object to be send in HTTP body of the request
     * @param inResponseType the response type of HTTP response
     * @param inUrlVariables the URL variables to be replaced in the URI's place holders
     * @param <S> the request object type
     * @param <T> the response object type
     * @return T the response object
     */
    public <S, T> T getService(final String inURI, final MultiValueMap<String, String> inHeaders, final S inRequest, final ParameterizedTypeReference<T> inResponseType, final Object... inUrlVariables) {
        return callService(HttpMethod.GET, inURI, inHeaders, inRequest, inResponseType, inUrlVariables);
    }

    /**
     * This will be called during initialization. It setups intercepter, error handler etc.
     */
    @PostConstruct
    public final void initialize() {
        // Attaching Intercepter
        final ClientHttpRequestInterceptor theClientHttpRequestInterceptor = new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(final HttpRequest inHttpRequest, final byte[] inBody, final ClientHttpRequestExecution inExecution) throws IOException {
                preInvoke(inHttpRequest, inBody);
                final ClientHttpResponse theResponse = inExecution.execute(inHttpRequest, inBody);
                postInvoke(theResponse);
                return theResponse;
            }
        };
        itsRestTemplate.setInterceptors(Collections.singletonList(theClientHttpRequestInterceptor));
        // Attaching Request Factory
        itsRestTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        // Byte Array Message Converter
        itsRestTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        // Attaching Error Handler
        itsRestTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public void handleError(final ClientHttpResponse inReponse) throws IOException {
                RestClient.this.handleError(inReponse);
            }

            @Override
            public boolean hasError(final ClientHttpResponse inReponse) throws IOException {
                return RestClient.this.hasError(inReponse);
            }
        });
    }

    /**
     * HTTP POST Service call
     *
     * @param inURI the URI of the requesting service
     * @param inHeaders the header parameters to be send in HTTP header of the request
     * @param inRequest the request object to be send in HTTP body of the request
     * @param inResponseType the response type of HTTP response
     * @param inUrlVariables the URL variables to be replaced in the URI's place holders
     * @param <S> the request object type
     * @param <T> the response object type
     * @return T the response object
     */
    public <S, T> T postService(final String inURI, final MultiValueMap<String, String> inHeaders, final S inRequest, final ParameterizedTypeReference<T> inResponseType, final Object... inUrlVariables) {
        return callService(HttpMethod.POST, inURI, inHeaders, inRequest, inResponseType, inUrlVariables);
    }

    /**
     * HTTP PUT Service
     *
     * @param inURI the URI of the requesting service
     * @param inHeaders the header parameters to be send in HTTP header of the request
     * @param inRequest the request object to be send in HTTP body of the request
     * @param inResponseType the response type of HTTP response
     * @param inUrlVariables the URL variables to be replaced in the URI's place holders
     * @param <S> the request object type
     * @param <T> the response object type
     * @return T the response object
     */
    public <S, T> T putService(final String inURI, final MultiValueMap<String, String> inHeaders, final S inRequest, final ParameterizedTypeReference<T> inResponseType, final Object... inUrlVariables) {
        return callService(HttpMethod.PUT, inURI, inHeaders, inRequest, inResponseType, inUrlVariables);
    }

    /**
     * Implementing class will provide the base URL of the service.
     *
     * @return String the base url
     */
    protected abstract String getBaseURL();

    /**
     * Implementing class will handle the error scenario.
     *
     * @param inReponse the response
     * @throws IOException it may throw IOException while response
     */
    protected abstract void handleError(ClientHttpResponse inReponse) throws IOException;

    /**
     * Implementing class will handle the exception scenario.
     *
     * @param inException the exception occured
     */
    protected abstract void handleException(RestClientException inException);

    /**
     * Implementing class will determine whether response has any error.
     *
     * @param inReponse the response object
     * @return boolean whether there is any error in response
     * @throws IOException it may throw IOException while response
     */
    protected abstract boolean hasError(ClientHttpResponse inReponse) throws IOException;

    /**
     * This will be invoked before service call. It logs the request details.
     *
     * @param inResponse the response object
     */
    protected void postInvoke(final ClientHttpResponse inResponse) {
        try (BufferedReader theBufferedReader = new BufferedReader(new InputStreamReader(inResponse.getBody()))) {
            final StringBuilder theInputStringBuilder = new StringBuilder(400);
            String line;
            while ( (line = theBufferedReader.readLine()) != null) {
                theInputStringBuilder.append(line);
                theInputStringBuilder.append('\n');
            }
            final String theResponseBody = StringUtils.abbreviate(theInputStringBuilder.toString(), 10000);
            LOGGER.info("Response:: Status Code [{}], Text [{}], Body [{}]", inResponse.getStatusCode(), inResponse.getStatusText(), theResponseBody);
        }catch (final IOException ex) {
            LOGGER.error("Failed to log response ", ex);
        }
    }

    /**
     * This will be invoked after service call. It logs the response details.
     *
     * @param inRequest the request object
     * @param inBody the body of the request
     */
    protected void preInvoke(final HttpRequest inRequest, final byte[] inBody) {
        String theRequestBody = new String(inBody);
        //For POST Requests, stop logging Attachment Data
        if (HttpMethod.POST == inRequest.getMethod() && StringUtils.contains(theRequestBody, "Content-Disposition: form-data; name=\"file\"")) {
            final String theMultipartDataBoundaryIdentifier = StringUtils.substringBefore(theRequestBody, "\r\n");
            final String[] theBodyParts = StringUtils.splitByWholeSeparator(theRequestBody, theMultipartDataBoundaryIdentifier);
            if (theBodyParts != null && theBodyParts.length > 1) {
                final String theJsonPart = theBodyParts[0];
                theRequestBody = "{" + StringUtils.substringBetween(theJsonPart, "{", "}") + "}";
            }
        }
        LOGGER.info("Request:: Method [{}], URI [{}], Body [{}]", inRequest.getMethod(), inRequest.getURI(), theRequestBody);
    }

    /**
     * Calls the service
     *
     * @param inHttpMethod the HTTP method of the request
     * @param inURI the URI of the requesting service
     * @param inHeaders the header parameters to be send in HTTP header of the request
     * @param inRequest the request object to be send in HTTP body of the request
     * @param inResponseType the response type of HTTP response
     * @param inUrlVariables the URL variables to be replaced in the URI's place holders
     * @param <S> the request object type
     * @param <T> the response object type
     * @return T the response object
     */
    private <S, T> T callService(final HttpMethod inHttpMethod, final String inURI, final MultiValueMap<String, String> inHeaders, final S inRequest, final ParameterizedTypeReference<T> inResponseType, final Object... inUrlVariables) {
        try {
            final String theURL = new StringBuffer(getBaseURL()).append(inURI).toString();
            final HttpEntity<S> theRequest = new HttpEntity<>(inRequest, inHeaders);
            final ResponseEntity<T> theResponse = itsRestTemplate.exchange(theURL, inHttpMethod, theRequest, inResponseType, inUrlVariables);
            return theResponse.getBody();
        }catch (final RestClientException ex) {
            handleException(ex);
            return null;
        }
    }

}
