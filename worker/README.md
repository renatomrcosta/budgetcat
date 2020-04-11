# WORKER

Bridges the scraper and storage.

## Endpoints

### GET
`/health`
Replies OK. Sanity check

### POST
`/work`
 
 Queries the scraper with the defined parameters:
 Triggers a call to scrape transactions, then  stores then locally. Takes the following Query Parameters:
 
 `from_date` and `end_date` -> ISO Date (beginning and end dates for query)
 
 `provider` -> String. Right now, only `n26` accepted
 
 `limit` -> Long. As means to limit the amount of requests
 

## Environment variables

SCRAPER_HOST: Address where the scraper is running
SCRAPER_USERNAME: username for basic auth configured in that application
SCRAPER_PASSWORD: username for basic auth configured in that application 

STORAGE_HOST: Address where the storage is running
STORAGE_USERNAME: username for basic auth configured in that application
STORAGE_PASSWORD: username for basic auth configured in that application 

Btw, basic auth is just OK for the current juncture.


THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.

