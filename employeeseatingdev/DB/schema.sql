CREATE DATABASE employee_seating_dev;

USE employee_seating_dev;

CREATE TABLE seating_chart (
    floor_seat_seq INT PRIMARY KEY IDENTITY(1,1),         -- 序號
    floor_no INT NOT NULL,                                -- 樓層編號
    seat_no VARCHAR(10) NOT NULL,                         -- 座位編號
    is_active BIT NOT NULL DEFAULT 1,                     -- 是否啟用（1=啟用、0=停用）
    note NVARCHAR(100),                                   -- 備註
    updated_at DATETIME DEFAULT GETDATE(),                -- 最後更新時間
    updated_by CHAR(5)                                    -- 最後更新者（員工編號）
);

CREATE TABLE employee (
    emp_id CHAR(5) PRIMARY KEY,                           -- 員工編號（固定長度5）
    name NVARCHAR(50) NOT NULL,                           -- 員工姓名
    email NVARCHAR(100),                                  -- 員工電子郵件
    is_active BIT NOT NULL DEFAULT 1,                     -- 是否在職（1=在職、0=離職）
    created_at DATETIME DEFAULT GETDATE(),                -- 建立時間
    updated_at DATETIME DEFAULT GETDATE(),                -- 更新時間
    floor_seat_seq INT,                                   -- 外鍵對應座位表
    FOREIGN KEY (floor_seat_seq) REFERENCES seating_chart(floor_seat_seq)
);