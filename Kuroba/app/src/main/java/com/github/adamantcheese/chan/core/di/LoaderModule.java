package com.github.adamantcheese.chan.core.di;

import com.github.adamantcheese.chan.core.cache.FileCacheV2;
import com.github.adamantcheese.chan.core.loader.impl.PostExtraContentLoader;
import com.github.adamantcheese.chan.core.loader.impl.PrefetchLoader;
import com.github.adamantcheese.chan.core.loader.impl.external_media_service.ExternalMediaServiceExtraInfoFetcher;
import com.github.adamantcheese.chan.core.loader.impl.external_media_service.YoutubeMediaServiceExtraInfoFetcher;

import org.codejargon.feather.Provides;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.schedulers.Schedulers;

public class LoaderModule {

    @Provides
    @Singleton
    public PrefetchLoader providePrefetchLoader(FileCacheV2 fileCacheV2) {
        return new PrefetchLoader(fileCacheV2);
    }

    @Provides
    @Singleton
    public PostExtraContentLoader providePostExtraContentLoader(
            NetModule.ProxiedOkHttpClient okHttpClient,
            @Named(ExecutorsManager.onDemandContentLoaderExecutorName) Executor onDemandContentLoaderExecutor
    ) {
        List<ExternalMediaServiceExtraInfoFetcher> fetchers = new ArrayList<>();
        fetchers.add(new YoutubeMediaServiceExtraInfoFetcher());

        return new PostExtraContentLoader(
                okHttpClient,
                Schedulers.from(onDemandContentLoaderExecutor),
                fetchers
        );
    }

}
