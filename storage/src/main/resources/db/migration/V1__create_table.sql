CREATE TABLE transaction
(
	id UUID NOT NULL
		CONSTRAINT transaction_pk
			PRIMARY KEY,
	data JSONB
);
