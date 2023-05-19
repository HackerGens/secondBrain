package notes.brain.secondbrain.DBHelper;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static class Subjects implements BaseColumns {
        public static final String TABLE_NAME = "subjects";
        public static final String COLUMN_NAME_SUBJECT_NAME = "subject_name";
    }

    public static class Tags implements BaseColumns {
        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_NAME_TAG_NAME = "tag_name";
    }

    public static class Topics implements BaseColumns {
        public static final String TABLE_NAME = "topics";
        public static final String COLUMN_NAME_TOPIC_NAME = "topic_name";
        public static final String COLUMN_NAME_SUBJECT_ID = "subject_id";
    }

    public static class TopicTags implements BaseColumns {
        public static final String TABLE_NAME = "topic_tags";
        public static final String COLUMN_NAME_TOPIC_ID = "topic_id";
        public static final String COLUMN_NAME_TAG_ID = "tag_id";
    }
}
