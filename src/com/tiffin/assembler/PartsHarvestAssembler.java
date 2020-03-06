package com.tiffin.assembler;

import com.tiffin.model.PartsHarvest;
import com.tiffin.model.PartsHarvestDetails;
import com.tiffin.model.ResponseWrapper;
import com.tiffin.view.PartsHarvestDetailsView;
import com.tiffin.view.PartsHarvestView;
import com.tiffin.view.ResponseWrapperView;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * Assembler for Parts Harvest
 */
@Component
public class PartsHarvestAssembler extends BaseAssembler {

    /**
     * Get All PartsHarvest Details
     *
     * @param inResponse as model for all parts harvest details
     * @return ResponseWrapperView for all parts harvest details
     */
    public ResponseWrapperView getPartHarvestDetailsResponse(final ResponseWrapper<PartsHarvest> inResponse) {
        final ResponseWrapperView theResponseWrapperView = extractMessages(inResponse);
        final PartsHarvest thePartsHarvest = inResponse.getResponse();
        // Check for empty
        if (thePartsHarvest == null) {
            return theResponseWrapperView;
        }
        // Populate the view
        final PartsHarvestView thePartsHarvestView = new PartsHarvestView();
        BeanUtils.copyProperties(thePartsHarvest, thePartsHarvestView);
        theResponseWrapperView.setResponse(thePartsHarvestView);
        return theResponseWrapperView;
    }

    /**
     * Get List of PartsHarvest details
     *
     * @param inPartsHarvestDetailsView the List of PartsHarvest view
     * @return List of PartsHarvest model
     */
    public List<PartsHarvestDetails> getPartsHarvest(final List<PartsHarvestDetailsView> inPartsHarvestDetailsView) {
        final List<PartsHarvestDetails> theAllPartsHarvestDetails = new ArrayList<>();
        // Check for empty
        if (inPartsHarvestDetailsView == null) {
            return theAllPartsHarvestDetails;
        }
        // Create list of PartsHarvest details
        for (final PartsHarvestDetailsView thePartsHarvestDetailsView : inPartsHarvestDetailsView) {
            final PartsHarvestDetails thePartsHarvestDetails = new PartsHarvestDetails();
            BeanUtils.copyProperties(thePartsHarvestDetailsView, thePartsHarvestDetails);
            theAllPartsHarvestDetails.add(thePartsHarvestDetails);
        }
        return theAllPartsHarvestDetails;
    }
}
