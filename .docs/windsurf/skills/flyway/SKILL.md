---
name: flyway
description: Standards for database migrations using Flyway in Spring Boot microservices. Use when creating, modifying, or reviewing SQL migration files.
---

# Flyway Migration Standards

## 1. Naming Convention

```
V{version}__{description}.sql
```

| Element | Format | Example |
|---------|--------|---------|
| Version | Semantic versioning | `V1.0.0`, `V1.0.1`, `V1.1.0` |
| Separator | Double underscore `__` | — |
| Description | snake_case | `create_customer_table` |
| Full example | — | `V1.0.0__create_customer_table.sql` |

## 2. Required ✅

- One migration per logical change (do not mix changes)
- Idempotent migrations when possible (`IF NOT EXISTS`, `IF EXISTS`)
- Include manual rollback documented in comments
- Test migrations locally before commit
- Use semantic versioning: `V1.0.0`, `V1.0.1`, `V1.1.0`

## 3. Forbidden ❌

- Modifying already executed migrations (changes checksum)
- Deleting applied migrations
- `DROP TABLE` or `DROP COLUMN` without backup/data migration
- Mixing DDL and DML in the same migration

## 4. Best Practices

- ✅ Always specify column types explicitly
- ✅ Add indexes for frequently queried columns
- ✅ Use `NOT NULL` with default values when possible
- ✅ Document rollback steps in SQL comments
- ❌ Never assume migration order beyond version number
- ❌ Never use `CASCADE` deletes without explicit approval

## 5. Example Migration

```sql
-- V1.2.0__add_email_to_customer.sql
-- Rollback: ALTER TABLE customer DROP COLUMN email;

ALTER TABLE customer
    ADD COLUMN IF NOT EXISTS email VARCHAR(255) NOT NULL DEFAULT '';

CREATE INDEX IF NOT EXISTS idx_customer_email
    ON customer (email);
```
