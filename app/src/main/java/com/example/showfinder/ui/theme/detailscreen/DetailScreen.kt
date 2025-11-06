package com.example.showfinder.ui.theme.detailscreen

import android.R.attr.label
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.showfinder.data.model.Media
import com.example.showfinder.utils.Resource
import kotlinx.serialization.descriptors.PrimitiveKind

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    imdbId: String,
    navController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val detailState by viewModel.detailState.collectAsState()

    LaunchedEffect(imdbId) {
        viewModel.loadDetailScreen(imdbId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            when (detailState) {
                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Error -> Text((detailState as Resource.Error).message)
                is Resource.Success -> {
                    val media = (detailState as Resource.Success<Media>).data
                    MediaDetail(
                        title = media.title,
                        year = media.year,
                        type = media.type,
                        poster = media.poster,
                        releaseDate = media.releaseDate,
                        overview = media.overview,
                        runtime = media.runtime,
                        genre = media.genre,
                        director = media.director,
                        writer = media.writer,
                        actors = media.actors,
                        rated = media.rated,
                        awards = media.awards
                    )
                }
            }
        }
    }
}

@Composable
fun MediaDetail(
    title: String,
    year: Int?,
    type: String,
    poster: String?,
    releaseDate: String?,
    overview: String?,
    runtime: String? = null,
    genre: String? = null,
    director: String? = null,
    writer: String? = null,
    actors: String? = null,
    rated: String? = null,
    awards: String? = null
) {

    val imageURL = poster ?: "https://cdn-icons-png.flaticon.com/512/4221/4221419.png"

    Column(
        modifier = Modifier
            .fillMaxSize().padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = imageURL,
            contentDescription = "Image",
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Text(text = title, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, modifier = Modifier.padding(top = 14.dp))


        Spacer(modifier = Modifier.height(8.dp))

        MediaInfoCard {
            InfoRow("Type", type)
            InfoRow("Year", year?.toString())
            InfoRow("Release Date", releaseDate)
            InfoRow("Runtime", runtime)
            InfoRow("Genre", genre)
            InfoRow("Rated", rated)
            InfoRow("Awards", awards)
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (director != null || writer != null || actors != null) {
            MediaInfoCard(title = "Credits") {
                InfoRow("Director", director)
                InfoRow("Writer", writer)
                InfoRow("Actors", actors)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        MediaInfoCard(title = "Overview") {
            Text(
                text = overview ?: "No overview available",
                fontSize = 16.sp
            )
        }

    }

}

@Composable
fun MediaInfoCard(
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            title?.let {
                Text(text = it, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            content()
        }
    }


}

@Composable
fun InfoRow(lable: String, value: String?) {
    if (!value.isNullOrBlank()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = lable,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                text = value,
                fontSize = 16.sp,
                maxLines = 1,
                softWrap = false
            )
        }
    }
}