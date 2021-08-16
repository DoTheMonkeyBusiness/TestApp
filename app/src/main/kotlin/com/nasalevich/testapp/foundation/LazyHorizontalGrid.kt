package com.nasalevich.testapp.foundation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalFoundationApi
fun <T> LazyHorizontalGrid(
    modifier: Modifier = Modifier,
    items: List<T>,
    numberOfRows: Int,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    contentSpacing: Dp = 0.dp,
    cellContent: @Composable ColumnScope.(T) -> Unit,
) {
    val columns = remember(items, numberOfRows) { items.chunked(numberOfRows) }

    LazyRow(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(contentSpacing)
    ) {
        items(columns) { columnItems ->
            Column(
                verticalArrangement = Arrangement.spacedBy(contentSpacing)
            ) {
                columnItems.forEach { content ->
                    cellContent(content)
                }
                if (columnItems.size < numberOfRows) {
                    repeat(numberOfRows - columnItems.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
