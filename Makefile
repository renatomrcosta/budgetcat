build:
	docker-compose build

run:
	docker-compose up

build-and-run:
	docker-compose up --build

run-scraper:
	docker-compose up scraper

run-storage:
	docker-compose up storage db

run-dependencies:
	docker-compose up db

.PHONY: build run run-scraper run-storage run-dependencies build-and-run
