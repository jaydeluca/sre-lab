-- Used in the docker-compose for bootstrapping

DROP DATABASE IF EXISTS "sre-lab";

CREATE DATABASE "sre-lab";

-- Prevent docker container from screaming at us
CREATE USER postgres SUPERUSER;