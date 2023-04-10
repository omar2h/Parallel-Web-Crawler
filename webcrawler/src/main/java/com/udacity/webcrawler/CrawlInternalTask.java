package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrawlInternalTask extends RecursiveAction {
    private final String url;
    private final Instant deadline;
    private final int maxDepth;
    private final Map<String, Integer> counts;
    private final Set<String> visitedUrls;
    private final Clock clock;
    private final PageParserFactory parserFactory;
    private final List<Pattern> ignoredUrls;

    public CrawlInternalTask(String url,
                             Instant deadline,
                             int maxDepth,
                             Map<String, Integer> counts,
                             Set<String> visitedUrls,
                             Clock clock,
                             PageParserFactory parserFactory,
                             List<Pattern> ignoredUrls) {
        this.url = url;
        this.deadline = deadline;
        this.maxDepth = maxDepth;
        this.counts = counts;
        this.visitedUrls = visitedUrls;
        this.clock = clock;
        this.parserFactory = parserFactory;
        this.ignoredUrls = ignoredUrls;
    }

    @Override
    protected void compute() {
        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return;
        }

        for (Pattern pattern : ignoredUrls) {
            if (pattern.matcher(url).matches()) {
                return;
            }
        }

        if (!visitedUrls.add(url)) {
            return;
        }
        PageParser.Result result = parserFactory.get(url).parse();
        for (Map.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            counts.compute(e.getKey(), (k, v) -> v == null ? e.getValue() : v + e.getValue());
        }

        List<CrawlInternalTask> subtasks =
                result.getLinks().parallelStream()
                        .map(link -> new CrawlInternalTask(
                                link,
                                deadline,
                                maxDepth - 1,
                                counts,
                                visitedUrls,
                                clock,
                                parserFactory,
                                ignoredUrls))
                        .collect(Collectors.toList());
        invokeAll(subtasks);
    }
}
