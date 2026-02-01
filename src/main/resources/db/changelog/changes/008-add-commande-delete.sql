-- liquibase formatted sql

-- changeset abdellatif:008-add-commande-delete
INSERT INTO permissions (name, description, resource, action) VALUES
('COMMANDE_DELETE', 'Supprimer une commande', 'COMMANDE', 'DELETE');

-- assign COMMANDE_DELETE to roles that already have COMMANDE_CANCEL
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.name = 'COMMANDE_DELETE'
WHERE EXISTS (
    SELECT 1
    FROM role_permissions rp
    JOIN permissions pc ON pc.id = rp.permission_id
    WHERE rp.role_id = r.id
      AND pc.name = 'COMMANDE_CANCEL'
);

-- rollback statements
-- rollback DELETE FROM role_permissions WHERE permission_id IN (SELECT id FROM permissions WHERE name='COMMANDE_DELETE');
-- rollback DELETE FROM permissions WHERE name='COMMANDE_DELETE';

