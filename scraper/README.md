# Scraper

Connects to a data source for transactions and provides a client that fetches transaction information.

Configuration settings are done via environment variables, which can be set up in a `.env` file in the root folder of this project

Currently supports n26 bank accounts. This software is provided as-is. Any usage of data such as emails and passwords is up to your own risk!

## Endpoints

### GET
`/health` (no auth needed)
Replies OK. Sanity check

### POST
`/scrape`
Triggers a call to scrape transactions. Takes the following Query Parameters:

`from_date` and `end_date` -> ISO Date (beginning and end dates for query)

`provider` -> String. Right now, only `n26` accepted

limit` -> Long. As means to limit the amount of requests

## Environment variables
APP_USERNAME: Username to be used by Spring security in this app (basic auth)
APP_PASSWORD: Password to be used by Spring security in this app (basic auth)
N26_USERNAME=your n26 account username
N26_PASSWORD=your n26 account password
N26_AUTH_TOKEN=N26 API Auth Token

The authentication mechanism was gleaned from https://github.com/guitmz/n26/. Many thanks for the awesome and readable code! 

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.

