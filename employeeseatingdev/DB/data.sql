USE employee_seating_dev;

-- 假資料 --
INSERT INTO seating_chart (floor_no, seat_no, is_active, note, updated_at, updated_by) VALUES
(1, '1', 1, NULL, GETDATE(), '10001'),
(1, '2', 1, NULL, GETDATE(), '10001'),
(1, '3', 1, NULL, GETDATE(), '10001'),
(1, '4', 1, NULL, GETDATE(), '10001'),
(2, '1', 1, NULL, GETDATE(), '10001'),
(2, '2', 1, NULL, GETDATE(), '10001'),
(2, '3', 1, NULL, GETDATE(), '10001'),
(2, '4', 1, NULL, GETDATE(), '10001'),
(3, '1', 1, NULL, GETDATE(), '10001'),
(3, '2', 1, NULL, GETDATE(), '10001'),
(3, '3', 1, NULL, GETDATE(), '10001'),
(3, '4', 1, NULL, GETDATE(), '10001'),
(4, '1', 1, NULL, GETDATE(), '10001'),
(4, '2', 1, NULL, GETDATE(), '10001'),
(4, '3', 1, NULL, GETDATE(), '10001'),
(4, '4', 1, NULL, GETDATE(), '10001');

INSERT INTO employee (emp_id, name, email, is_active, created_at, updated_at, floor_seat_seq) VALUES
('13040', '王小明', 'xiaoming@example.com', 1, GETDATE(), GETDATE(), 9),
('17081', '陳美麗', 'meili@example.com', 1, GETDATE(), GETDATE(), 10),
('12006', '李大仁', 'daren@example.com', 1, GETDATE(), GETDATE(), 3),
('16142', '林志遠', 'zhiyuan@example.com', 1, GETDATE(), GETDATE(), 7),
('11221', '周玉珍', 'yuzhen@example.com', 1, GETDATE(), GETDATE(), 12),
('16722', '曾佳怡', 'jiayi@example.com', 1, GETDATE(), GETDATE(), 15),
('20001', '楊靜雯', 'jingwen@example.com', 1, GETDATE(), GETDATE(), NULL),
('11305', '王大陸', 'wang@example.com', 1, GETDATE(), GETDATE(), NULL),
('17903', '楊振紳', 'peter@example.com', 1, GETDATE(), GETDATE(), NULL),
('22230', '陳伸維', 'jerrychen@example.com', 1, GETDATE(), GETDATE(), NULL),
('00068', '張智凱', 'zhikai@example.com', 0, GETDATE(), GETDATE(), NULL);