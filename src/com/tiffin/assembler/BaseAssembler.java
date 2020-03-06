package com.tiffin.assembler;

import com.tiffin.constant.WebApplicationConstants;
import com.tiffin.exception.TiffinAppBuinessException;
import com.delta.techops.materials.phv.model.BaseModel;
import com.delta.techops.materials.phv.model.Message;
import com.delta.techops.materials.phv.model.ResponseWrapper;
import com.delta.techops.materials.phv.view.MessageView;
import com.delta.techops.materials.phv.view.ResponseWrapperView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Base Assembler. All other assemblers will extend this to have common functionalities.
 */
public class BaseAssembler {

    protected BaseAssembler() {
        // Prevent direct instantiation
    }

    /**
     * Extract Messages
     *
     * @param <T> the response type
     * @param inResponse the response model
     * @return ResponseWrapperView the response view
     */
    public <T extends BaseModel> ResponseWrapperView extractMessages(final ResponseWrapper<T> inResponse) {
        final ResponseWrapperView theResponseWrapperView = new ResponseWrapperView();
        theResponseWrapperView.setStatus(inResponse.getStatus());
        if (inResponse.getMessages() != null) {
            final List<MessageView> thMessageViews = new ArrayList<>();
            for (final Message theMessage : inResponse.getMessages()) {
                final MessageView theMessageView = new MessageView();
                theMessageView.setMessageID(theMessage.getMessageID());
                theMessageView.setMessageType(theMessage.getMessageType());
                theMessageView.setMessageDetail(theMessage.getMessageDetail());
                thMessageViews.add(theMessageView);
            }
            theResponseWrapperView.setMessages(thMessageViews);
        }
        return theResponseWrapperView;
    }

    /**
     * Get Request URI with parameters
     *
     * @param inServiceUri the base URL
     * @param inQueryParams the query parameters to be added in URI
     * @param inPathparams the path variables to be replaced in URI
     * @return String the URL with parameters
     */
    public String getRequestUriWithParameters(final String inServiceUri, final Map<String, String> inQueryParams, final Object... inPathparams) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(inServiceUri);
        for (final Map.Entry<String, String> theQueryParam : inQueryParams.entrySet()) {
            builder.queryParam(theQueryParam.getKey(), theQueryParam.getValue());
        }
        return builder.buildAndExpand(inPathparams).toUriString();
    }

    /**
     * Get Uploaded File
     *
     * @param inFile the uploaded file
     * @param inHeader the authentication related details
     * @return MultiValueMap the uploaded file request
     */
    public MultiValueMap<String, Object> getUploadedFile(final MultipartFile inFile, final MultiValueMap<String, String> inHeader) {
        final MultiValueMap<String, Object> theFileMap = new LinkedMultiValueMap<>();
        theFileMap.add("file", convertMultiPartToFileSystemResource(inFile));
        setMultipartContentType(inHeader);
        return theFileMap;
    }

    /**
     * Get Uploaded Files with data
     *
     * @param inFiles the uploaded files
     * @param inData the form data
     * @param inHeader the authentication related details
     * @return MultiValueMap the uploaded file request
     */
    public MultiValueMap<String, Object> getUploadedFilesWithData(final List<MultipartFile> inFiles, final String inData, final MultiValueMap<String, String> inHeader) {
        final MultiValueMap<String, Object> theRequestMap = new LinkedMultiValueMap<>();
        for (final MultipartFile theFile : inFiles) {
            theRequestMap.add("file", convertMultiPartToFileSystemResource(theFile));
            theRequestMap.add("filename", theFile.getOriginalFilename());
        }
        theRequestMap.add("data", inData);
        setMultipartContentType(inHeader);
        return theRequestMap;
    }

    /**
     * Convert MultiPart File To FileSystemResource
     *
     * @param inFile the input MultiPart File
     * @return the result FileSystemresource which needs to be uploaded
     */
    private FileSystemResource convertMultiPartToFileSystemResource(final MultipartFile inFile) {
        //Creates a new file corresponding to the multi part file and deletes it after FileSystemResource creation
        //        final File theConvFile = new File(inFile.getOriginalFilename());
        //        theConvFile.deleteOnExit();
        final String theOriginalFileName = inFile.getOriginalFilename();
        try {
            final Path tempFile = Files.createTempFile(StringUtils.substringBeforeLast(theOriginalFileName, "."), "." + StringUtils.substringAfterLast(theOriginalFileName, "."));
            Files.write(tempFile, inFile.getBytes());
//            FileOutputStream fos = new FileOutputStream(tempFile.toFile());
            //writing the content of the multipart file
//            fos.write(inFile.getBytes());
            return new FileSystemResource(tempFile.toFile());
        }catch (final IOException inException) {
            //Sends a generic failure message if any exception occurs while writing in to the file
            throw new WebApplicationException(WebApplicationConstants.PROP_ERROR_INVALID_UPLOAD_REQUEST);
        }
    }

    /**
     * Set Multipart ContentType
     *
     * @param inHeader the request header
     */
    private void setMultipartContentType(final MultiValueMap<String, String> inHeader) {
        inHeader.add("Content-Type", "multipart/form-data");
    }

}
