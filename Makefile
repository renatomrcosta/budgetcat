build:
	docker-compose build

run:
	docker-compose up

run-scraper:
	docker-compose up scraper

.PHONY: build run run-scraper
