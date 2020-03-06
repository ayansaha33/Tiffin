package com.tiffin.common;

import com.tiffin.config.WebPropertyConfig;
import com.tiffin.constant.WebApplicationConstants;
import com.tiffin.exception.TiffinAppBuinessException;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

/**
 * Parts Harvest Service Client. It is used to call Parts Harvest Services.
 */
@Component
public class PartsHarvestServiceClient extends RestClient {

    @Autowired
    private WebPropertyConfig itsProperties;

    /**
     * Get Base URL
     */
    @Override
    protected String getBaseURL() {
       // return itsProperties.getPropertyForCurrentEnv(WebApplicationConstants.PROP_PHV_SERVICE_URL);
        
        return "http://localhost:9081/PartsHarvestService";
    }

    /**
     * This will be called when 'hasError' will return false
     */
    @Override
    protected void handleError(final ClientHttpResponse inReponse) {
        throw new WebApplicationException(WebApplicationConstants.PROP_ERROR_PHV_SERVICE_DOWN);
    }

    /**
     * This will be called when an any exception occurrs while calling the service
     */
    @Override
    protected void handleException(final RestClientException inException) {
        throw new WebApplicationException(WebApplicationConstants.PROP_ERROR_PHV_SERVICE_DOWN, inException);
    }

    /**
     * Determines whether response has any error or not.
     */
    @Override
    protected boolean hasError(final ClientHttpResponse inReponse) throws IOException {
        // Since PHV Service always return HTTP 200. Other status means something wrong with it.
        return !HttpStatus.OK.equals(inReponse.getStatusCode());
    }

}
