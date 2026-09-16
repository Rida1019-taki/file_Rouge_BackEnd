SET @sql := IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
     WHERE TABLE_SCHEMA = DATABASE()
       AND TABLE_NAME = 'utilisateurs'
       AND COLUMN_NAME = 'actif') = 0,
    'ALTER TABLE utilisateurs ADD COLUMN actif BOOLEAN DEFAULT TRUE',
    'SELECT 1'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;