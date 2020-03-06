# Budgetcat

A monorepo for small, discrete applications to fetch transaction information for budgeting purposes.

For the initial commits, very specific implementations will be used. I can generify later.

## Parts

* Scraper (shitty name pending) - connects to the bank / transaction holder and returns the transactions in a GET endpoint

* Storage (TODO) - Stores transaction data somewhere

* Cron (TODO) - Calls scraper in a timely manner and stores shit

* Visualizer (TODO) - Sees / analyses stored data
