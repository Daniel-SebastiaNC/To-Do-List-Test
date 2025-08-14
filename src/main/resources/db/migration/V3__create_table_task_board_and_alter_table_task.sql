CREATE TABLE tb_task_board (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    status progress_status NOT NULL DEFAULT 'NOT_STARTED'
);

ALTER TABLE tb_task
    ADD COLUMN task_board_id UUID;

ALTER TABLE tb_task
    ADD CONSTRAINT fk_task_board
        FOREIGN KEY (task_board_id) REFERENCES tb_task_board(id);