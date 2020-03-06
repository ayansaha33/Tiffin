package com.tiffin.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Base Model. Other models will extends this.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseModel {

}