package com.eisen.module.product.exception;

import java.util.List;

import com.eisen.common.exception.DomainException;

public class NotAllProductsFoundException extends DomainException{
    private List<Long> notFoundProductIds;

    public NotAllProductsFoundException(List<Long> notFoundProductIds, String errorMessage) {
        super(404, errorMessage);
        this.notFoundProductIds = notFoundProductIds;
    }

    public List<Long> getNotFoundProductIds() {
        return notFoundProductIds;
    }

    public void setNotFoundProductIds(List<Long> notFoundProductIds) {
        this.notFoundProductIds = notFoundProductIds;
    }
}
