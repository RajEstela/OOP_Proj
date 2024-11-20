package org.CSPT.sc2001_grp1_proj1.entity;

/**
 * Enum representing different roles in the system.
 * These roles define the various user types with different access rights within the application.
 */

public enum RolesEnum {
    /** Represents a doctor role in the system. */
    Doctor,

    /** Represents an administrator role in the system. */
    Administrator,

    /** Represents a pharmacist role in the system. */
    Pharmacist,

    /** Represents a patient role in the system. */
    Patient,

    /** Represents a security guard role in the system. */
    SecurityGuard,

    /** Represents a janitor role in the system. */
    Janitor,

    /** Represents a nurse role in the system. */
    Nurse,

    /** Represents a pending role, typically used for users awaiting role assignment. */
    Pending
}
