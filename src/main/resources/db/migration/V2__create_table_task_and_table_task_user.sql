CREATE TYPE progress_status AS ENUM ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELED');

CREATE TABLE tb_task (
    id UUID PRIMARY KEY,

    title VARCHAR(255) NOT NULL,

    description TEXT,

    deadline TIMESTAMP,

    status progress_status NOT NULL DEFAULT 'NOT_STARTED',

    priority SMALLINT NOT NULL DEFAULT 0
);

CREATE TABLE tb_task_user (

    task_id UUID NOT NULL,

    user_id UUID NOT NULL,

    PRIMARY KEY (task_id, user_id),

      CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tb_task(id) ON DELETE CASCADE,
      CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE
);