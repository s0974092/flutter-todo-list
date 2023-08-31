import com.example.plugins.*
import entities.Task
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import tables.Tasks
import io.ktor.serialization.jackson.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    configureRouting()

    install(ContentNegotiation) {
        jackson {

        }
    }

    Database.connect(
        url= "jdbc:h2:mem:todo_api;DB_CLOSE_DELAY=-1;",
        driver = "org.h2.Driver"
    )

    transaction {
        SchemaUtils.create(Tasks)
    }

    transaction {
        for (i in 1..10) {
            Task.new {
               title = "Task $i"
               completed = listOf(true, false, false).shuffled().first()
               createdAt = DateTime.now()
               updatedAt = DateTime.now()
            }
        }
    }
}
