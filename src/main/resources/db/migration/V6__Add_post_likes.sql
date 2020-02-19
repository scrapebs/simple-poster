create table post_likes (
    post_id bigint not null references post,
    user_id bigint not null references usr,
    primary key (post_id, user_id)
)