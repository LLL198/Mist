-- Add title for episode tag
ALTER TABLE `movie_download_record`
ADD COLUMN `title` varchar(255) DEFAULT NULL COMMENT 'Title or episode tag (movie raw, tv SxxExx or SxxExx-Exx)';
