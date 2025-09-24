
CREATE TABLE IF NOT EXISTS news_media_link (
   news_id BIGINT NOT NULL,
   media_id BIGINT NOT NULL,
   PRIMARY KEY (news_id, media_id),
   FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE,
   FOREIGN KEY (media_id) REFERENCES media (id) ON DELETE CASCADE
);
