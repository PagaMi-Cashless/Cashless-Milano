/**
 * @author Ayoub Chouak (a.chouak)
 * @brief  Nexi Exception class
 *
 */
package io.github.app.exception


class NexiPaymentException: Exception
{
    /**
     * @brief Enumeration for Nexi API error codes
     */
    enum class NexiError
    {
        INPUT_MISMATCH,
        INFO_NOT_FOUND,
        HMAC_MISMATCH,
        HMAC_MISSING,
        TIMESTAMP_EXPIRED
    }

    companion object
    {
        val NEXI_ERR_CODES: Map<Int, NexiError> = mapOf(
                1 to NexiError.INPUT_MISMATCH,
                2 to NexiError.INFO_NOT_FOUND,
                3 to NexiError.HMAC_MISMATCH,
                4 to NexiError.HMAC_MISSING,
                5 to NexiError.TIMESTAMP_EXPIRED
        )
    }

    constructor() : super() {
        // Unimplemented
    }

    constructor(what: String): super(what) {
        // Unimplemented
    }

    constructor(what: String, throw0: Throwable): super(what, throw0) {
        // Unimplemented
    }

    constructor(what: String, throw0: Throwable, flag0: Boolean, flag1: Boolean)
        : super(what, throw0, flag0, flag1) {
        // Unimplemented
    }
}