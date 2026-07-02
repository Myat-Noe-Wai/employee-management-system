INSERT INTO roles(name)
VALUES ('ADMIN'), ('EMPLOYEE');

INSERT INTO permissions(name)
VALUES
('VIEW_EMPLOYEE'),
('CREATE_EMPLOYEE'),
('UPDATE_EMPLOYEE'),
('DELETE_EMPLOYEE'),
('VIEW_JOB_TITLE'),
('CREATE_JOB_TITLE'),
('UPDATE_JOB_TITLE'),
('DELETE_JOB_TITLE'),
('VIEW_ALL_ATTENDANCE'),
('CLOCK_IN'),
('CLOCK_OUT'),
('VIEW_OWN_ATTENDANCE'),
('APPLY_LEAVE'),
('APPROVE_LEAVE'),
('REJECT_LEAVE'),
('VIEW_ALL_LEAVE'),
('VIEW_OWN_LEAVE'),
('DELETE_LEAVE'),
('EXPORT_LEAVE'),
('VIEW_USER');

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ADMIN';

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r
JOIN permissions p ON p.name IN (
    'CLOCK_IN',
    'CLOCK_OUT',
    'VIEW_OWN_ATTENDANCE',
    'APPLY_LEAVE',
    'VIEW_OWN_LEAVE'
)
WHERE r.name = 'EMPLOYEE';