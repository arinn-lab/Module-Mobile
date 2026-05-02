package com.example.songpediacompose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.songpediacompose.ui.theme.TextBrown
import com.example.songpediacompose.ui.theme.TextDark
import com.example.songpediacompose.ui.theme.White
import com.example.songpediacompose.viewmodel.SongViewModel

val HeaderColor = Color(0xFF8B6343)
val ButtonBrown = Color(0xFF8B6343)
val CardBg = Color(0xFFF0EAE2)

@Composable
fun HomeScreen(
    viewModel: SongViewModel,
    onDetailClick: (Song) -> Unit,
    onLanguageClick: () -> Unit
) {
    val context = LocalContext.current
    val songs by viewModel.songs.observeAsState(emptyList())
    val prefs = context.getSharedPreferences("settings", android.content.Context.MODE_PRIVATE)
    val lang = prefs.getString("language", "id") ?: "id"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(HeaderColor)
                .padding(start = 16.dp, end = 4.dp, top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )

            IconButton(
                onClick = onLanguageClick,
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Language,
                    contentDescription = "Change Language",
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.label_highlighted),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(
                        start = 16.dp, end = 16.dp,
                        top = 8.dp, bottom = 4.dp
                    )
                )

                LazyRow(
                    modifier = Modifier.height(180.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(songs) { song ->
                        AsyncImage(
                            model = song.imageRes,
                            contentDescription = song.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.label_all_songs),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark,
                    modifier = Modifier.padding(
                        start = 16.dp, end = 16.dp,
                        top = 16.dp, bottom = 4.dp
                    )
                )
            }

            items(songs) { song ->
                SongItem(
                    song = song,
                    lang = lang,
                    onDetailClick = onDetailClick,
                    onSpotifyClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.spotifyUrl))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun SongItem(
    song: Song,
    lang: String,
    onDetailClick: (Song) -> Unit,
    onSpotifyClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = song.imageRes,
                contentDescription = song.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = song.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = TextDark,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = song.year.toString(),
                        fontSize = 12.sp,
                        color = TextBrown,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = song.artist,
                    fontSize = 12.sp,
                    color = TextDark,
                    modifier = Modifier.padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (lang == "id") song.descriptionId else song.descriptionEn,
                    fontSize = 11.sp,
                    color = TextBrown,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onSpotifyClick,
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonBrown),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.btn_spotify),
                            color = White,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onDetailClick(song) },
                        colors = ButtonDefaults.buttonColors(containerColor = ButtonBrown),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.btn_detail),
                            color = White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}