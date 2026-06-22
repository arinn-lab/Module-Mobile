package com.example.tugaslazylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn

@Composable
fun ItemListScreen(viewModel: ItemViewModel) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_main)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            top = 16.dp,
            bottom = 8.dp
        )
    ) {
        itemsIndexed(viewModel.items) { index, item ->
            val isEven = (index + 1) % 2 == 0
            val bgColor = if (isEven) colorResource(R.color.item_bg_even)
            else colorResource(R.color.item_bg_odd)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 3.dp)
                    .clickable {
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_item_click, item.id),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = item.imageRes,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.text_title)
                        )
                        Text(
                            text = item.description,
                            fontSize = 13.sp,
                            color = colorResource(R.color.text_description)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Switch(
                            checked = item.isToggled,
                            onCheckedChange = { isChecked ->
                                viewModel.toggleItem(index, isChecked)
                                if (isChecked) {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.toast_switch_on, item.id),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colorResource(R.color.switch_thumb_active),
                                checkedTrackColor = colorResource(R.color.color_primary),
                                uncheckedThumbColor = colorResource(R.color.switch_thumb_inactive),
                                uncheckedTrackColor = colorResource(R.color.switch_track_inactive)
                            )
                        )

                        Button(
                            onClick = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.toast_btn_aksi, item.id),
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.btn_aksi_bg)
                            ),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 7.dp),
                            modifier = Modifier.heightIn(min = 26.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.btn_aksi),
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}