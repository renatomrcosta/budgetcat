build:
	docker-compose build

run:
	docker-compose up

run-scraper:
	docker-compose up scraper

run-storage:
	docker-compose up storage db

run-dependencies:
	docker-compose up db

.PHONY: build run run-scraper run-storage run-dependencies
