# Budgetcat

A monorepo for small, discrete applications to fetch transaction information for budgeting purposes. Aggregates transactions in a single DB for querying / analysis purposes

For the initial commits, very specific implementations will be used. I can generify later.

## Parts

* Scraper - connects to the bank / transaction holder and returns the transaction data via a GET endpoint

* Storage - Stores transaction data in a Postgres DB, using a JSONB column

* Worker - Bridges the scraper / storage together.


# Setup

See the readme in each project to understand how they function.

Set the necessary environment variables in a `.env` file for each project.

# Running
Configure each project using its `.env` files containing their necessary Environment variables (or provide them directly).

Use the commands `make build` and `make run` to build and run the all parts, or look into the Makefile to see each bespoke command (like `make run-scraper`)
