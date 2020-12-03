CREATE TABLE IF NOT EXISTS public.machines (
	id uuid NOT NULL,
	active bool NULL,
	created_at timestamp NULL,
	name varchar(255) NULL,
	updated_at timestamp NULL,
	CONSTRAINT machines_pkey PRIMARY KEY (id)
);