/**
 *  @author Ayoub Chouak (a.chouak)
 *  @brief  User DAO Componente
 *  @todo   Remove
 *
 */

package io.github.app.schema

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class UserData
{
    private var users: HashMap<Int, UserSchema> = hashMapOf(
            0 to UserSchema(id = 0, name="Farjad Ali", email="farjadali@outlook.it"),
            1 to UserSchema(id = 1, name="Adil Mehmood", email="adilmehmood@gmail.com")
    )
    private var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    /*
     * DAO Functions
     */
    fun create(name: String, email: String) {
        val curId = lastId.getAndIncrement()
        users[curId] = UserSchema(id=curId, name=name, email=email)
    }
}