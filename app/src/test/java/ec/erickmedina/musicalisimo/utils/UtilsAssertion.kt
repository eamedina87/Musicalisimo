package ec.erickmedina.musicalisimo.utils

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.TopAlbumModel

class UtilsAssertion {

    companion object {

        fun assertAlbumModel(album: AlbumModel) {
            with(album) {
                assertThat(name).isNotEmpty()
                assertThat(artist).isNotEmpty()
                assertThat(localId).isNotNull()
                assertThat(remoteId).isNotNull()
                assertThat(images).isNotNull()
                assertThat(tracks).isNotNull()
                assertThat(tags).isNotNull()
                assertThat(listeners).isAtLeast(0)
                assertThat(playcount).isAtLeast(0)
                assertThat(summary).isNotNull()
                assertThat(publication).isNotNull()
                assertThat(description).isNotNull()
            }
        }

        fun assertArtistModel(artist: ArtistModel) {
            with(artist) {
                assertThat(id).isNotNull()
                assertThat(listeners).isAtLeast(0)
                assertThat(name).isNotEmpty()
                assertThat(images).isNotNull()
                images.forEach {
                    with(it) {
                        assertThat(url).isNotNull()
                        assertThat(size).isNotNull()
                    }
                }
            }
        }

        fun assertTopAlbumModel(topAlbum: TopAlbumModel) {
            with(topAlbum) {
                assertThat(name).isNotEmpty()
                assertThat(name).isNotEmpty()
                assertThat(playcount).isAtLeast(0)
                assertThat(url).isNotNull()
                assertThat(artist).isNotNull()
                assertThat(artist.name).isNotEmpty()
                assertThat(artist.id).isNotNull()
                image.forEach { image ->
                    Truth.assertThat(image).isNotNull()
                    Truth.assertThat(image.url).isNotNull()
                    Truth.assertThat(image.size).isNotNull()
                }
            }
        }
    }

}