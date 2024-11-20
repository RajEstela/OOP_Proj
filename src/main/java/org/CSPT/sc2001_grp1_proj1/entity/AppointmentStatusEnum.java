package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * Enumeration representing the various statuses of an appointment.
 * This enum is used to define and manage the possible states an appointment can have.
 */

public enum AppointmentStatusEnum {
    /**
     * The appointment is pending approval or further processing.
     */
    Pending,
    /**
     * The appointment has been approved and is confirmed.
     */
    Approved,
    /**
     * The appointment request has been rejected.
     */
    Rejected,
    /**
     * The appointment has been cancelled by either the user or the system.
     */
    Cancelled,
    /**
     * The appointment slot is available for booking.
     */
    Available,
    /**
     * The appointment has been completed successfully.
     */
    Completed
}
