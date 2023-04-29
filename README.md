# UdaciSearch

Legacy Web Crawler is a Java project that uses concurrent programming techniques to enhance a legacy web crawler to take advantage of multi-core architectures. The project aims to increase the performance of the web crawler by implementing multi-threading techniques.

## Project Overview
The Legacy Web Crawler reads configuration from a JSON file, downloads and parses multiple HTML documents in parallel, and records popular web terms in an output file. The program uses the ForkJoinPool class to run multiple threads on a pool to fetch and process multiple web pages in parallel.

The program also includes a method profiling tool that measures the efficiency of the crawler and proves the benefits of the parallel crawler.

## Features
- Uses concurrent programming techniques to enhance a legacy web crawler to take advantage of multi-core architectures
- Reads configuration from a JSON file
- Downloads and parses multiple HTML documents in parallel
- Records popular web terms in an output file
- Implements multi-threading techniques using the ForkJoinPool class
- Includes a method profiling tool to measure the efficiency of the crawler and prove the benefits of the parallel crawler
