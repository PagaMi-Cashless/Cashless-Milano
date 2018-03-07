/**
 *  @author Ayoub Chouak (a.chouak)
 *  @brief  MongoDB Configuration and Client Connection Manager
 *  @todo   Read the data from a XML file
 *
 */

package io.github.app.mongo

import java.util.concurrent.*;

// MongoDB
import com.mongodb.MongoClient

object MongoManager
{
    var mongoClientPool: ConcurrentLinkedQueue<MongoClient> = ConcurrentLinkedQueue()

}