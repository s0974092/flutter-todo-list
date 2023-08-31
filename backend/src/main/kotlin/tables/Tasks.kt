package tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.jodatime.datetime
import org.joda.time.DateTime

object Tasks: IntIdTable() {
    val title: Column<String> = varchar("title", 255)
    val completed: Column<Boolean> = bool("completed")
    val createdAt: Column<DateTime> = datetime("created_at")
    val updateAt: Column<DateTime> = datetime("updated_at")
}