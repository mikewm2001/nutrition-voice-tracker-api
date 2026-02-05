CREATE TABLE IF NOT EXISTS nutrition_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,

    -- UI display + audit trail
    label VARCHAR(255) NOT NULL,         -- e.g. "Chicken breast (raw)"
    raw_input VARCHAR(500),              -- e.g. "500 grams of chicken breast, raw" (nullable)

    -- Source metadata
    source VARCHAR(20) NOT NULL,         -- 'manual' | 'voice' | 'api'
    external_food_id VARCHAR(100),       -- nullable (e.g., USDA/Edamam id)
    amount NUMERIC(10,2),                -- nullable for some sources
    unit VARCHAR(20),                    -- 'g','oz','ml','serving' (nullable)

    -- Nutrition (final computed values you can always aggregate)
    calories INTEGER NOT NULL,
    protein_g NUMERIC(8,2) NOT NULL,
    carbs_g NUMERIC(8,2) NOT NULL,
    fat_g NUMERIC(8,2) NOT NULL,

    micros JSONB,                        -- nullable (future-proof)

    logged_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
    );

CREATE INDEX IF NOT EXISTS idx_entries_user_created
    ON nutrition_entries(user_id, created_at DESC);
