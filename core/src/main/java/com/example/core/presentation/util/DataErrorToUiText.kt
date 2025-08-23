package com.example.core.presentation.util

import com.example.core.R
import com.example.core.domain.util.DataError
import com.example.core.presentation.ui.UiText

fun DataError.asUiText(): UiText {
    return when(this) {

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_too_many_requests
        )
        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )
        DataError.Network.INVALID_CREDENTIALS -> UiText.StringResource(
            R.string.invalid_credentials
        )
        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )
        DataError.Network.EXCEED_REQUEST_LIMIT -> UiText.StringResource(
            R.string.error_exceed_limit
        )
        else -> UiText.StringResource(R.string.error_unknown)
    }
}