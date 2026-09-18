package com.una.embyhub.movie.event;

import com.una.embyhub.movie.entity.MoviePtSubscribeEntity;

public record MoviePtSubscribeSavedEvent(MoviePtSubscribeEntity subscribe, boolean created) {
}
