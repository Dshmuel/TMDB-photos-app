package com.dimovsoft.shutterfly.ui.components.movies_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dimovsoft.shutterfly.R
import com.dimovsoft.shutterfly.model.Configuration
import com.dimovsoft.shutterfly.model.Movie

@Composable
fun MovieItem(
	movie: Movie,
) {
	Card(
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
		modifier = Modifier
			.padding(horizontal = dimensionResource(id = R.dimen.margin_normal))
			.padding(bottom = dimensionResource(id = R.dimen.card_bottom_margin))
	) {
		Column(Modifier.fillMaxWidth()) {
			Box {
				MoviePoster(
					model = "${Configuration.currentConfiguration.completeBaseUrl}${movie.posterPath}",
					contentDescription = movie.title,
					Modifier
						.fillMaxWidth()
						.height(dimensionResource(id = R.dimen.movie_item_image_height)),
					contentScale = ContentScale.Crop
				)
				Box(
					Modifier
						.padding(8.dp)
						.align(Alignment.BottomEnd)
						.rotate(-10f)
						.background(color = Color.Red, shape = ShapeDefaults.Medium)
						.border(1.dp, Color.White, shape = ShapeDefaults.Medium)
						.padding(4.dp)
				) {
					Text(
						text = movie.year,
						maxLines = 1,
						style = MaterialTheme.typography.titleLarge,
						color = Color.White,
					)
				}
			}
			Text(
				text = movie.title ?: stringResource(R.string.unknown_name),
				textAlign = TextAlign.Center,
				minLines = 2,
				maxLines = 2,
				style = MaterialTheme.typography.titleMedium,
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = dimensionResource(id = R.dimen.margin_normal))
					.wrapContentWidth(Alignment.CenterHorizontally)
			)
		}
	}
}

@Preview
@Composable
fun MoviesItemPreview() {
	val movie = Movie(951546, "", "Reign of Chaos", "2022-04-12")
	MovieItem(movie = movie)
}