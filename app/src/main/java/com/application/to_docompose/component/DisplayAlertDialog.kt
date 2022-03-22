package com.application.to_docompose.component

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.application.to_docompose.R

@Composable
fun DisplayAlertDialog(
    title: String,
    text: String,
    confirmClick: () -> Unit,
    closeClick: () -> Unit,
    openDialog: Boolean
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                closeClick()
            },
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = text,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        confirmClick()
                        closeClick()
                    },
                ) {
                    Text(text = stringResource(R.string.yes))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = closeClick) {
                    Text(text = stringResource(R.string.no))
                }
            }
        )
    }
}