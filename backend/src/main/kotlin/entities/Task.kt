package entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.joda.time.DateTime
import tables.Tasks

class Task(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Task>(table = Tasks)

    var title: String by Tasks.title
    var completed: Boolean by Tasks.completed
    var createdAt: DateTime by Tasks.createdAt
    var updatedAt: DateTime by Tasks.updateAt
}