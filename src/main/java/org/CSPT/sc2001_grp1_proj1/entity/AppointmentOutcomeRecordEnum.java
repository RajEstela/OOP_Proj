package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * Represents the possible outcomes for an appointment in the system.
 * This enum defines a fixed set of constants that indicate the current
 * status or result of an appointment.
 */
public enum AppointmentOutcomeRecordEnum {
    /**
     * Indicates that the appointment is pending and has not yet been confirmed or dispensed.
     */
    Pending,
    /**
     * Indicates that the appointment has been confirmed.
     */
    Confirmed,

    /**
     * Indicates that the medication or services associated with the appointment
     * have been dispensed.
     */
    Dispensed
}
