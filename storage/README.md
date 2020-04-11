# Storage

Takes an object (any) and tries to store it in a PostgresDB.

Uses flyway for DB migration.

Does not use JPA due to issues handling jsonb columns.


## Endpoints

### GET
`/health` (no auth needed)
Replies OK. Sanity check

### POST
`/store`
Stores a transaction. 

Takes a query param:
`id` -> UUID. Unique identifier for a given transaction.

Its body can be any sort of POJO to be stored as a JSON in a JSONB column

## Environment variables
APP_USERNAME: Username to be used by Spring security in this app (basic auth)
APP_PASSWORD: Password to be used by Spring security in this app (basic auth)

DB_USER: Username for accessing the provided DB (see the docker-compose file for more details)
DB_HOST: Hostname of your DB
DB_PORT: Port of your DB
POSTGRES_DB: DB name
POSTGRES_PASSWORD: DB password


THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.

