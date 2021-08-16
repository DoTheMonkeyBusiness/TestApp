package com.nasalevich.testapp.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.nasalevich.testapp.R
import com.nasalevich.testapp.domain.model.ImageModel
import com.nasalevich.testapp.extension.Constant.NUMBER_OF_ALL_ITEMS
import com.nasalevich.testapp.extension.Constant.NUMBER_OF_COLUMNS
import com.nasalevich.testapp.extension.Constant.NUMBER_OF_ROWS
import com.nasalevich.testapp.foundation.LazyHorizontalGrid
import com.nasalevich.testapp.presentation.CellModel
import com.nasalevich.testapp.presentation.HomeIntent
import com.nasalevich.testapp.presentation.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.lang.Integer.max

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val images by viewModel.state.collectAsState()

    var numberOfItems by remember { mutableStateOf(NUMBER_OF_ALL_ITEMS) }
    val cellModelList = remember(images, numberOfItems) { images.toCellModelList(numberOfItems) }

    Scaffold(
        topBar = {
            TopBar(
                onAddClick = {
                    numberOfItems = maxOf(numberOfItems, images.size) + 1
                    coroutineScope.launch {
                        viewModel.userIntent.send(HomeIntent.AddImage)
                    }
                },
                onReloadClick = {
                    numberOfItems = NUMBER_OF_ALL_ITEMS
                    coroutineScope.launch {
                        viewModel.userIntent.send(HomeIntent.ReloadAll)
                    }
                },
            )
        }
    ) { padding ->
        HomeContent(
            modifier = Modifier.padding(padding),
            items = cellModelList,
        )
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onReloadClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(id = R.string.application_name)) },
        actions = {
            TextButton(
                onClick = onReloadClick,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onPrimary)
            ) {
                Text(text = stringResource(id = R.string.reload_all))
            }
            IconButton(onClick = onAddClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_rounded),
                    contentDescription = null,
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    items: List<CellModel>,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val contentPadding = PaddingValues(2.dp)
        val contentSpacing = 2.dp
        val itemWidth = remember(maxWidth) {
            calculateItemWidth(
                availableWidth = maxWidth,
                paddings = contentPadding,
                contentSpacing = contentSpacing,
                itemsCount = NUMBER_OF_COLUMNS,
            )
        }
        LazyHorizontalGrid(
            modifier = Modifier.wrapContentHeight(),
            items = items,
            numberOfRows = NUMBER_OF_ROWS,
            contentPadding = contentPadding,
            contentSpacing = contentSpacing,
        ) { content ->
            val cellModifier = Modifier
                .weight(1f)
                .width(itemWidth)
                .clip(MaterialTheme.shapes.medium)

            when (content) {
                is CellModel.HasImage -> ImageContent(
                    modifier = cellModifier,
                    imageModel = content.imageModel,
                )
                is CellModel.Placeholder -> PlaceholderContent(
                    modifier = cellModifier,
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ImageContent(
    modifier: Modifier = Modifier,
    imageModel: ImageModel
) {
    val painter = rememberImagePainter(
        data = imageModel.imageUrl,
        builder = {
            size(200)
        }
    )
    val isPlaceholderVisible: Boolean = remember(painter.state) {
        when (painter.state) {
            is ImagePainter.State.Success -> false
            else -> true
        }
    }

    Image(
        modifier = modifier
            .placeholder(
                visible = isPlaceholderVisible,
                highlight = PlaceholderHighlight.shimmer()
            ),
        contentScale = ContentScale.Crop,
        painter = painter,
        contentDescription = null,
    )
}

@Composable
private fun PlaceholderContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.placeholder(
            visible = true,
            highlight = PlaceholderHighlight.shimmer()
        ),
    ) {}
}

private fun calculateItemWidth(
    availableWidth: Dp,
    paddings: PaddingValues,
    contentSpacing: Dp,
    itemsCount: Int,
    layoutDirection: LayoutDirection = LayoutDirection.Ltr,
): Dp {
    val layoutPaddingsWidth = paddings.calculateLeftPadding(layoutDirection) + paddings.calculateRightPadding(layoutDirection)
    val itemsSpacingWidth = (contentSpacing * (itemsCount - 1))

    return (availableWidth - itemsSpacingWidth - layoutPaddingsWidth) / itemsCount
}

private fun List<ImageModel>.toCellModelList(numberOfItems: Int): List<CellModel> {
    val placeholderListSize = max(numberOfItems - this.size, 0)

    return listOf(
        this.map { CellModel.HasImage(it) },
        List(placeholderListSize) { CellModel.Placeholder }
    ).flatten()
}
