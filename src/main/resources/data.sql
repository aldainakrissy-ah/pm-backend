-- Insert sample projects first (required for foreign key)
INSERT INTO project (id, name) VALUES (1, 'Project Alpha');
INSERT INTO project (id, name) VALUES (2, 'Project Beta');
INSERT INTO project (id, name) VALUES (3, 'Project Gamma');

-- Insert sample tasks (make sure project_id matches an existing project)
INSERT INTO task (name, priority, due_date, assignee, status, project_id) VALUES
('Design UI', 1, '2025-07-01', 'Alice', 'PENDING', 1),
('Develop Backend', 2, '2025-07-05', 'Bob', 'IN_PROGRESS', 1),
('Write Docs', 3, '2025-07-10', 'Carol', 'COMPLETED', 2),
('Testing', 2, '2025-07-15', 'Dave', 'PENDING', 3);
