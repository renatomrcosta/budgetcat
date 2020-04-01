build:
	docker-compose build

run:
	docker-compose up

run-scraper:
	docker-compose up scraper

run-storage:
	docker-compose up storage

.PHONY: build run run-scraper run-storage
