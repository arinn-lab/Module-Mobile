package com.example.songpediacompose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.songpediacompose.ui.theme.TextDark
import com.example.songpediacompose.viewmodel.SongViewModel

val DetailTextBrown = Color(0xFF6D4B3F)

@Composable
fun DetailScreen(
    viewModel: SongViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val song: Song? by viewModel.selectedSong.observeAsState(initial = null)
    val prefs = context.getSharedPreferences("settings", android.content.Context.MODE_PRIVATE)
    val lang = prefs.getString("language", "id") ?: "id"

    if (song != null) {
        val s = song!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {


            AsyncImage(
                model = s.imageRes,
                contentDescription = s.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(all = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )


            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)) {


                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = s.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = s.year.toString(),
                        fontSize = 16.sp,
                        color = DetailTextBrown,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }


                Text(
                    text = s.artist,
                    fontSize = 16.sp,
                    color = DetailTextBrown,
                    modifier = Modifier.padding(top = 4.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                ) {
                    drawRect(color = Color(0xFF3E2723))
                }
                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = stringResource(R.string.label_album).uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextDark,
                    letterSpacing = 0.1.sp
                )
                Text(
                    text = s.album,
                    fontSize = 14.sp,
                    color = DetailTextBrown,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = stringResource(R.string.label_genre).uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextDark,
                    letterSpacing = 0.1.sp
                )
                Text(
                    text = s.genre,
                    fontSize = 14.sp,
                    color = DetailTextBrown,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))


                Text(
                    text = stringResource(R.string.label_description).uppercase(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextDark,
                    letterSpacing = 0.1.sp
                )
                Text(
                    text = if (lang == "id") s.descriptionId else s.descriptionEn,
                    fontSize = 14.sp,
                    color = DetailTextBrown,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(top = 2.dp, bottom = 20.dp)
                )
            }
        }
    }
}