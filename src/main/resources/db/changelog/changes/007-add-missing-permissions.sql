-- liquibase formatted sql

-- changeset tricol:007-add-missing-permissions
INSERT INTO permissions (name, description, resource, action) VALUES 
('BON_SORTIE_UPDATE', 'Permission to update bon de sortie', 'BON_SORTIE', 'UPDATE'),
('BON_SORTIE_DELETE', 'Permission to delete bon de sortie', 'BON_SORTIE', 'DELETE'),
('STATISTIQUES_READ', 'Permission to read statistics', 'STATISTIQUES', 'READ');

-- Add STATISTIQUES_READ permission to all roles
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE p.name = 'STATISTIQUES_READ';

-- Add missing BON_SORTIE permissions to appropriate roles
INSERT INTO role_permissions (role_id, permission_id) 
SELECT r.id, p.id 
FROM roles r, permissions p 
WHERE r.name IN ('ADMIN', 'MAGASINIER') 
AND p.name IN ('BON_SORTIE_UPDATE', 'BON_SORTIE_DELETE');