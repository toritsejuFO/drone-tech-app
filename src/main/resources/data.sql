INSERT INTO drone (sn, model, weight_limit, battery_capacity, state, created_at, updated_at)
VALUES
    ('SN001', 'HEAVYWEIGHT', 500, 100, 'LOADING', NOW(), NOW()),
    ('SN002', 'CRUISERWEIGHT', 300, 25, 'LOADED', NOW(), NOW()),
    ('SN003', 'MIDDLEWEIGHT', 200, 75, 'LOADING', NOW(), NOW()),
    ('SN004', 'CRUISERWEIGHT', 350, 90, 'LOADING', NOW(), NOW()),
    ('SN005', 'CRUISERWEIGHT', 400, 34, 'LOADING', NOW(), NOW()),
    ('SN006', 'HEAVYWEIGHT', 500, 66, 'LOADED', NOW(), NOW()),
    ('SN007', 'LIGHTWEIGHT', 100, 45, 'LOADING', NOW(), NOW()),
    ('SN008', 'MIDDLEWEIGHT', 250, 100, 'IDLE', NOW(), NOW()),
    ('SN009', 'CRUISERWEIGHT', 450, 89, 'DELIVERING', NOW(), NOW()),
    ('SN010', 'HEAVYWEIGHT', 500, 16, 'IDLE', NOW(), NOW())
;

-- Populate 'medication' table with random data
INSERT INTO medication (name, weight, code, image_url, drone_id, created_at, updated_at)
VALUES
    ('Medication A1', 100, 'CODE1', './uploads/customer.jpeg', 1, NOW(), NOW()),
    ('Medication A2', 100, 'CODE2', './uploads/customer.jpeg', 1, NOW(), NOW()),
    ('Medication B1', 150, 'CODE3', './uploads/customer.jpeg', 2, NOW(), NOW()),
    ('Medication B2', 150, 'CODE4', './uploads/customer.jpeg', 2, NOW(), NOW()),
    ('Medication C1', 40, 'CODE5', './uploads/customer.jpeg', 3, NOW(), NOW()),
    ('Medication C2', 130, 'CODE6', './uploads/customer.jpeg', 3, NOW(), NOW()),
    ('Medication D1', 120, 'CODE7', './uploads/customer.jpeg', 4, NOW(), NOW()),
    ('Medication D2', 150, 'CODE8', './uploads/customer.jpeg', 4, NOW(), NOW()),
    ('Medication E1', 100, 'CODE9', './uploads/customer.jpeg', 5, NOW(), NOW()),
    ('Medication E2', 150, 'CODE10', './uploads/customer.jpeg', 5, NOW(), NOW()),
    ('Medication F1', 100, 'CODE11', './uploads/customer.jpeg', 6, NOW(), NOW()),
    ('Medication F2', 400, 'CODE12', './uploads/customer.jpeg', 6, NOW(), NOW()),
    ('Medication G1', 85, 'CODE13', './uploads/customer.jpeg', 7, NOW(), NOW()),
    ('Medication I1', 250, 'CODE17', './uploads/customer.jpeg', 9, NOW(), NOW()),
    ('Medication I2', 200, 'CODE18', './uploads/customer.jpeg', 9, NOW(), NOW())
;
