package org.CSPT.sc2001_grp1_proj1.entity;

public class ReplenishmentRequest {

    public String requestID;
    public String medicineName;
    public String requestedBy;
    public String status;
    public int quantity;

    public ReplenishmentRequest(String medicineName, int quantity, String requestedBy, String status)
    {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.requestedBy = requestedBy;
        this.status = status;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    public String getRequestID()
    {
        return requestID;
    }

    public String getMedicineName()
    {
        return medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RequestID: " + requestID + ", Medicine: " + medicineName + ", Quantity: " + quantity +
                ", Requested By: " + requestedBy + ", Status: " + status;
    }

}