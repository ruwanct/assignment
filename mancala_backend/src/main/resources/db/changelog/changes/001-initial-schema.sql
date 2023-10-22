-- Create the board table
create table board
(
    cols integer not null,
    rows integer not null,
    id   bigserial
        primary key,
    pits bytea
);
-- Create the game table

create table game
(
    next_turn                 integer,
    prev_turn                 integer,
    started                   boolean not null,
    stones_per_pit            integer,
    winner                    integer not null,
    board_id                  bigint
        unique
        constraint fk7xfop7fngh26l311rh6nevt09
            references board,
    game_id                   bigserial
        primary key,
    last_sown_pit             bytea
);

-- Create the player table
create table player
(
    id        bigserial
        primary key,
    player_id bigint
        constraint fk8goo1lxlnml01yd0b44vurmu4
            references game,
    name      varchar(255)
);