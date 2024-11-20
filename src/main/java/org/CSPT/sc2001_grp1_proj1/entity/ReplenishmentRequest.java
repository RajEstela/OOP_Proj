package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * Represents a replenishment request for a particular medicine, including its status, quantity, and requester details.
 */

public class ReplenishmentRequest {

    /** The unique ID of the replenishment request. */
    protected String requestID;

    /** The name of the medicine requested for replenishment. */
    public String medicineName;

    /** The name of the person who requested the replenishment. */
    public String requestedBy;

    /** The current status of the replenishment request (e.g., "New", "Approved"). */
    public String status;

    /** The quantity of the medicine requested for replenishment. */
    public int quantity;

    /**
     * Constructs a new ReplenishmentRequest with the given details.
     *
     * @param requestID The unique ID of the replenishment request.
     * @param medicineName The name of the medicine being requested.
     * @param quantity The quantity of the medicine being requested.
     * @param requestedBy The name of the person requesting the replenishment.
     * @param status The current status of the replenishment request.
     */

    public ReplenishmentRequest(String requestID, String medicineName, int quantity, String requestedBy, String status)
    {
        this.requestID = requestID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.requestedBy = requestedBy;
        this.status = status;
    }

    /**
     * Sets the request ID of the replenishment request.
     * 
     * @param requestID The unique ID to set for the replenishment request.
     */

    public void setRequestID(String requestID) 
    {
        this.requestID = requestID;
    }

    /**
     * Gets the request ID of the replenishment request.
     * 
     * @return The unique ID of the replenishment request.
     */

    public String getRequestID()
    {
        return requestID;
    }

    /**
     * Gets the name of the medicine requested for replenishment.
     * 
     * @return The name of the medicine.
     */

    public String getMedicineName()
    {
        return medicineName;
    }

    /**
     * Gets the quantity of the medicine requested for replenishment.
     * 
     * @return The quantity of the medicine requested.
     */

    public int getQuantity() 
    {
        return quantity;
    }

    /**
     * Gets the name of the person who requested the replenishment.
     * 
     * @return The name of the requester.
     */

    public String getRequestedBy() 
    {
        return requestedBy;
    }

    /**
     * Gets the current status of the replenishment request.
     * 
     * @return The status of the request (e.g., "New", "Approved").
     */

    public String getStatus() 
    {
        return status;
    }

     /**
     * Sets the status of the replenishment request.
     * 
     * @param status The status to set for the request.
     */

    public void setStatus(String status) 
    {
        this.status = status;
    }

    /**
     * Displays the details of the replenishment request, including the request ID, medicine name, quantity, 
     * requester, and status.
     */

    public void displayReplenishmentRequestDetails()
    {
        System.out.println("Request ID: " + getRequestID());
        System.out.println("Medicine Name: " + getMedicineName());
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Requestd By: " + getRequestedBy());
        System.out.println("Status: " + getStatus());
    }

    /**
     * Returns a string representation of the replenishment request.
     * 
     * @return A string containing the request ID, medicine name, quantity, requester, and status.
     */

    @Override
    public String toString() 
    {
        return "RequestID: " + requestID + ", Medicine: " + medicineName + ", Quantity: " + quantity +
                ", Requested By: " + requestedBy + ", Status: " + status;
    }

}
