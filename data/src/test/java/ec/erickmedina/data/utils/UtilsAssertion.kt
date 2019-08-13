package ec.erickmedina.data.utils

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import ec.erickmedina.data.entity.LastFmResponses
import ec.erickmedina.domain.models.AlbumModel
import ec.erickmedina.domain.models.ArtistModel
import ec.erickmedina.domain.models.TopAlbumModel

class UtilsAssertion {

    companion object {

        fun assertArtistEntity(artist: LastFmResponses.Artist) {
            with(artist) {
                Truth.assertThat(name).isNotEmpty()
                Truth.assertThat(url).isNotEmpty()
                Truth.assertThat(listeners).isAtLeast(0)
                Truth.assertThat(streamable).isAtLeast(0)
                Truth.assertThat(image).isNotEmpty()
                image.forEach { img ->
                    Truth.assertThat(img).isNotNull()
                    Truth.assertThat(img.text).isNotEmpty()
                    Truth.assertThat(img.size).isNotEmpty()
                }
            }
        }

        fun assertTopAlbumEntity(album: LastFmResponses.TopAlbum) {
            with(album) {
                Truth.assertThat(name).isNotEmpty()
                Truth.assertThat(playcount).isAtLeast(0)
                Truth.assertThat(url).isNotEmpty()
                Truth.assertThat(artist).isNotNull()
                Truth.assertThat(artist.name).isNotEmpty()
                Truth.assertThat(artist.mbid).isNotEmpty()
                Truth.assertThat(artist.url).isNotEmpty()
                image.forEach { image ->
                    Truth.assertThat(image).isNotNull()
                    Truth.assertThat(image.text).isNotNull()
                    Truth.assertThat(image.size).isNotNull()
                }
            }
        }

        fun assertAlbumEntity(album: LastFmResponses.Album) {
            with(album) {
                Truth.assertThat(name).isNotEmpty()
                Truth.assertThat(artist).isNotEmpty()
                Truth.assertThat(mbid).isNotEmpty()
                Truth.assertThat(url).isNotEmpty()
                Truth.assertThat(listeners).isAtLeast(0)
                Truth.assertThat(playcount).isAtLeast(0)
                Truth.assertThat(image).isNotEmpty()
                image.forEach {
                    Truth.assertThat(it).isNotNull()
                    Truth.assertThat(it.text).isNotEmpty()
                    Truth.assertThat(it.size).isNotNull()
                }
                Truth.assertThat(tracks).isNotNull()
                Truth.assertThat(tracks.track).isNotEmpty()
                tracks.track.forEach {
                    Truth.assertThat(it).isNotNull()
                    with(it) {
                        Truth.assertThat(name).isNotEmpty()
                        Truth.assertThat(artist).isNotNull()
                        Truth.assertThat(mbid).isNotEmpty()
                        Truth.assertThat(url).isNotEmpty()
                        Truth.assertThat(attr).isNotEmpty()
                    }
                }
                Truth.assertThat(tags).isNotNull()
                Truth.assertThat(tags?.tag).isNotNull()
                tags?.tag?.forEach {
                    Truth.assertThat(it).isNotNull()
                    with(it) {
                        Truth.assertThat(name).isNotEmpty()
                        Truth.assertThat(url).isNotEmpty()
                    }
                }
                Truth.assertThat(wiki).isNotNull()
                with(wiki!!) {
                    Truth.assertThat(content).isNotEmpty()
                    Truth.assertThat(published).isNotEmpty()
                    Truth.assertThat(summary).isNotEmpty()
                }
            }
        }

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