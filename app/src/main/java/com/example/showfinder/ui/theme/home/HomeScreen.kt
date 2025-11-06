package com.example.showfinder.ui.theme.home

import android.adservices.ondevicepersonalization.RenderOutput
import android.widget.Space
import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.showfinder.data.model.Movie
import com.example.showfinder.data.model.SearchItem
import com.example.showfinder.data.model.TvShow
import com.example.showfinder.utils.Resource
import com.example.showfinder.utils.Screen
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {

    val mediaState by viewModel.mediaState.collectAsState()
    val searchState by viewModel.searchState.collectAsState()

    var selectedTab by remember { mutableStateOf("movie") }
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Name of the Application
                    if (!isSearching) {
                        Text("ShowFinder", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    } else {
                        // Text for search movie and show
                        TextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                if (searchQuery.length > 2) {
                                    viewModel.searchQuery(searchQuery)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            placeholder = { Text("Search movies or shows...") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                modifier = Modifier.statusBarsPadding(),
                // ChnageIcon
                actions = {
                    IconButton(
                        onClick = { isSearching = !isSearching }
                    ) {
                        Icon(
                            imageVector = if (!isSearching) Icons.Default.Search
                            else Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (!isSearching) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ToggleButton("Movie", selectedTab == "movie") {
                        selectedTab = "movie"
                    }
                    ToggleButton("Tv Show", selectedTab == "tvshow") {
                        selectedTab = "tvshow"
                    }
                }
                when (mediaState) {
                    is Resource.Loading -> ShimmerListPlacehold()
                    is Resource.Success -> {
                        val (movie, show) = (mediaState as Resource.Success).data
                        val list = if (selectedTab == "movie") movie else show
                        MediaList(list, navController )
                    }

                    is Resource.Error -> {
                        ErrorText((mediaState as Resource.Error).message)
                    }
                }
            }else{
                when(searchState){
                    is Resource.Loading -> ShimmerListPlacehold()
                    is Resource.Success -> {
                        val (movies, shows) = (searchState as Resource.Success).data
                        val list  : List<Any> = movies + shows
                        MediaList(list, navController)
                    }
                    is Resource.Error -> {
                        ErrorText((mediaState as Resource.Error).message)
                    }
                }
            }
        }
    }


}

@Composable
fun MediaList(
    list: List<Any>,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val posterState by viewModel.posterState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
    ) {
        items(list) { item ->

            val title: String
            val year: String?
            val type: String?
            val imdbID: String?
            val poster: String?

            when (item) {
                is Movie -> {
                    title = item.title
                    type = "Movie"
                    year = item.year?.toString()
                    imdbID = item.imdb_id
                    poster = item.poster
                }

                is TvShow -> {
                    title = item.title
                    type = "Tv Show"
                    year = item.year?.toString()
                    imdbID = item.imdb_id
                    poster = item.poster
                }

                is SearchItem -> {
                    title = item.name ?: ""
                    type = item.type
                    year = item.year?.toString()
                    imdbID = item.tmbdID?.toString()
                    poster = item.poster
                }

                else -> {
                    title = ""
                    type = null
                    year = null
                    imdbID = null
                    poster = null
                }
            }

            var finalPoster by remember { mutableStateOf<String?>(poster) }

            if (finalPoster.isNullOrEmpty() && !imdbID.isNullOrEmpty()) {
                LaunchedEffect(imdbID) {
                    viewModel.fetchPoster(imdbID)
                }
            }

            LaunchedEffect(posterState) {
                if (posterState is Resource.Success && finalPoster.isNullOrEmpty() ){
                    finalPoster = (posterState as Resource.Success<String?>).data
                }
            }


            val placeHolder = "https://cdn-icons-png.flaticon.com/512/4221/4221419.png"
            val imageUrl = finalPoster?:placeHolder

            Card (
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable{
                        imdbID?.let {
                            navController.navigate(Screen.DetailScreem.creatRoute(it))
                        }
                    },
                colors = CardDefaults.cardColors(Color(0xFFF3F3F3))
            ){
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Image",
                        modifier = Modifier.size(100.dp).background(Color.LightGray)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Column (
                        modifier = Modifier.weight(1f)
                    ){
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        year?.let { Text("Year: $it") }
                        type?.let { Text("Type: $it") }
                    }
                }
            }


        }

    }


}

@Composable
fun ErrorText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text,
            color = Color.Red,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ToggleButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            if (isSelected) Color.Blue
            else Color.Gray
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 6.dp else 2.dp
        ),
        modifier = Modifier
            .height(48.dp)
            .widthIn(min = 120.dp)
    ) {
        Text(
            text, color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun ShimmerListPlacehold() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shimmer()
    ) {
        repeat(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
                    .background(
                        Color.Gray.copy(alpha = 0.3f)
                    )
            )
        }
    }

}